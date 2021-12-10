package com.backinfile.portal.net;

import com.backinfile.portal.Settings;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class NetUtils {
    public static DatagramPacket buildBroadcastMsg(String msg) {
        return new DatagramPacket(Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8),
                new InetSocketAddress(Settings.BROADCAST_ADDRESS, Settings.GAME_SERVER_UDP_PORT));
    }

    public static DatagramPacket buildMsg(String msg, InetSocketAddress address) {
        return new DatagramPacket(Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8), address);
    }

    public static String getString(DatagramPacket msg) {
        ByteBuf byteBuf = msg.content();
        return byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8).toString();
    }
}
