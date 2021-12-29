package com.backinfile.portal.net;

import com.backinfile.portal.Log;
import com.backinfile.portal.Res;
import com.backinfile.portal.Settings;
import com.backinfile.support.Time2;
import com.backinfile.support.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import jdk.internal.joptsimple.internal.Strings;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;

public class ServerLocateManager {
    private static final String SEARCH_CODE = "search";
    private static final String SERVER_RESPONSE_CODE = "server";
    private static final long CLIENT_REQUEST_INTERVAL = Time2.SEC * 3;

    public static Set<String> serverAddressList = new CopyOnWriteArraySet<>();

    private static Channel serverChannel = null;
    private static Channel clientChannel = null;


    public static void locateServerByHtml() {
        Net.HttpRequest httpRequest = new HttpRequestBuilder()
                .newRequest()
                .method(Net.HttpMethods.GET)
                .url(Settings.SERVER_INFO_URL)
                .build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                Log.net.info("locateServerByHtml get statusCode = {}", statusCode);
                if (statusCode == HttpStatus.SC_OK) {
                    String result = httpResponse.getResultAsString();
                    List<String> ipList = parseServerIPFromHtml(result);
                    Log.net.info("parse ip address: {}", ipList);
                    Gdx.files.local(Res.SERVER_IP_FILE_PATH)
                            .writeString(Strings.join(ipList, "\n"), false);
                }
            }

            @Override
            public void failed(Throwable t) {
                Log.net.error("locateServerByHtml get failed", t);
            }

            @Override
            public void cancelled() {
                Log.net.info("locateServerByHtml get cancelled");
            }
        });
    }

    private static final Pattern IP_PATTERN = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

    private static List<String> parseServerIPFromHtml(String content) {
        List<String> ipList = new ArrayList<>();
        for (String ip : content.split("\n")) {
            if (IP_PATTERN.matcher(ip).matches()) {
                ipList.add(ip);
            }
        }
        return ipList;
    }

    public static List<String> parseServerIPFromHtml() {
        FileHandle fileHandle = Gdx.files.local(Res.SERVER_IP_FILE_PATH);
        if (!fileHandle.exists()) {
            return new ArrayList<>();
        }
        List<String> ipList = new ArrayList<>();
        for (String ip : fileHandle.readString().split("\n")) {
            if (IP_PATTERN.matcher(ip).matches()) {
                ipList.add(ip);
            }
        }
        return ipList;
    }

    public static void startSearchInLAN() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class).handler(new ClientDatagramHandler());
            clientChannel = bootstrap.bind(Settings.GAME_CLIENT_UDP_PORT).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stopSearch() {
        if (clientChannel != null) {
            clientChannel.close();
            clientChannel = null;
        }
    }

    public static void serverStartResponse() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class).handler(new ServerDatagramHandler());
            serverChannel = bootstrap.bind(Settings.GAME_SERVER_UDP_PORT).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void serverStopResponse() {
        if (serverChannel != null) {
            serverChannel.close();
            serverChannel = null;
        }
    }


    static class ServerDatagramHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            Log.net.info("server udp start response");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String ip = msg.sender().getAddress().getHostAddress();

            String content = NetUtils.getString(msg);
            if (!content.equals(SEARCH_CODE)) {
                return;
            }
            DatagramPacket response = NetUtils.buildMsg(SERVER_RESPONSE_CODE, new InetSocketAddress(ip, Settings.GAME_CLIENT_UDP_PORT));
            ctx.writeAndFlush(response);
            Log.net.info("server udp read from {}, response finish", ip);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            Log.net.info("server udp stop response");
        }
    }

    static class ClientDatagramHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        private ClientSearchThread clientSearchThread = null;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            clientSearchThread = new ClientSearchThread(ctx.channel());
            clientSearchThread.start();
            Log.net.info("client udp start search");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String ip = msg.sender().getAddress().getHostAddress();
            String content = NetUtils.getString(msg);
            Log.net.info("client udp read from {} content {}", ip, content);
            if (content.equals(SERVER_RESPONSE_CODE)) {
                ServerLocateManager.serverAddressList.add(ip);
                Log.net.info("locate server:{}", ip);
                if (clientSearchThread != null) {
                    clientSearchThread.stopSearch();
                }
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            if (clientSearchThread != null) {
                clientSearchThread.stopSearch();
            }
            Log.net.info("client udp stop search");
        }
    }

    static class ClientSearchThread extends Thread {
        private volatile boolean stop = false;
        private final Channel channel;

        public ClientSearchThread(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            while (channel.isActive()) {
                if (stop) {
                    break;
                }
                if (!ServerLocateManager.serverAddressList.isEmpty()) {
                    break;
                }

                DatagramPacket datagramPacket = NetUtils.buildBroadcastMsg(SEARCH_CODE);
                channel.writeAndFlush(datagramPacket);
                Log.net.info("client udp send request for search server");
                Utils.sleep(CLIENT_REQUEST_INTERVAL);
            }
        }

        public void stopSearch() {
            stop = true;
        }
    }
}
