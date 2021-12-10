package com.backinfile.portal.net;

import com.backinfile.portal.Log;
import com.backinfile.portal.Settings;
import com.backinfile.support.Time2;
import com.backinfile.support.Utils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ServerLocateManager {
    public static String serverAddress = "";

    private static Channel serverChannel = null;
    private static Channel clientChannel = null;

    public static void startSearch() {
        try {
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class).handler(new ClientDatagramHandler());
            clientChannel = bootstrap.bind(Settings.GAME_SERVER_UDP_PORT).sync().channel();
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
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String ip = msg.sender().getAddress().getHostAddress();
            Log.net.info("server udp read from {}", ip);
        }
    }

    static class ClientDatagramHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        private ClientSearchThread clientSearchThread = null;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);

            clientSearchThread = new ClientSearchThread(ctx.channel());
            clientSearchThread.start();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String ip = msg.sender().getAddress().getHostAddress();
            Log.net.info("udp read from {}", ip);
        }
    }

    static class ClientSearchThread extends Thread {
        private boolean stop = false;
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
                if (!Utils.isNullOrEmpty(ServerLocateManager.serverAddress)) {
                    break;
                }

                new DatagramPacket(Unpooled.copiedBuffer("search", StandardCharsets.UTF_8), new InetSocketAddress("", 100));

                Utils.sleep(Time2.SEC);
            }
        }

        public void stopSearch() {
            stop = true;
        }
    }
}
