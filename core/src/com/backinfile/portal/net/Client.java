package com.backinfile.portal.net;

import com.backinfile.portal.Log;
import com.backinfile.portal.Settings;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class Client extends Thread {
    public static Channel Channel = null;

    @Override
    public void run() {
        try {
            startClient(Settings.LOCAL_HOST, Settings.GAME_CLIENT_PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startClient(String host, int port) throws InterruptedException {
        // 第一步，创建线程池
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 第二步，创建启动类
            Bootstrap b = new Bootstrap();
            // 第三步，配置各组件
            b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                    // 连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    // 启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，默认的心跳间隔是7200s即2小时。
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 配置Channel参数，nodelay没有延迟，true就代表禁用Nagle算法，减小传输延迟。
                    // 理解可参考：https://blog.csdn.net/lclwjl/article/details/80154565
                    .option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new Decoder(), new Encoder(), new ClientHandler());
                    pipeline.addLast(new ExceptionHandler());
                }
            });

            Log.client.info("start connect:{}", port);
            // 第四步，开启监听
            Channel = b.connect().sync().channel();
            Log.client.info("connected: {}:{}", host, port);
            Channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
