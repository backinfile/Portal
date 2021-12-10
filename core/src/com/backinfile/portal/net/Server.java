package com.backinfile.portal.net;

import com.backinfile.portal.Log;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public void start(int port) {
        try {
            startServer(port);
        } catch (InterruptedException e) {
            Log.net.error("listen start error", e);
        }
    }

    public void startServer(int port) throws InterruptedException {
        // 第一步，创建线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 第二步，创建启动类
            ServerBootstrap b = new ServerBootstrap();
            // 第三步，配置各组件
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true).childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new Decoder(), new Encoder(), new ServerHandler());
                            pipeline.addLast(new ExceptionHandler());
                        }
                    });

            Log.server.info("start listen:{}", port);
            // 第四步，开启监听
            Channel channel = b.bind(port).sync().channel();
            Log.server.info("listened: {}", port);
            channel.closeFuture().sync();
//            channel.closeFuture().addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture future) throws Exception {
//                    bossGroup.shutdownGracefully();
//                    workerGroup.shutdownGracefully();
//                }
//            });
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
            Log.server.info("close");
        }
    }

    public void stopServer() {
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        server.startServer(5555);
    }
}