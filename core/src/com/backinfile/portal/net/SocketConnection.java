package com.backinfile.portal.net;

import com.backinfile.portal.Log;
import com.backinfile.support.Time2;
import com.backinfile.support.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SocketConnection implements Connection {
    public static final String TAG = SocketConnection.class.getSimpleName();

    private Socket socket;
    private ConcurrentLinkedQueue<GameMessage> sendList = new ConcurrentLinkedQueue<GameMessage>();
    private ConcurrentLinkedQueue<GameMessage> reciveList = new ConcurrentLinkedQueue<GameMessage>();
    private InputStream inputStream;
    private OutputStream outputStream;
    private long time;
    public static final int HZ = 1;

    public String name;
    private long id;

    private final byte[] readBytes = new byte[1024];

    public SocketConnection(long id, Socket socket) {
        this.socket = socket;
        this.id = id;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.net.error("error in getStream from socket: {}", e.getMessage());
            close();
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameMessage getGameMessage() {
        return reciveList.poll();
    }

    public ConcurrentLinkedQueue<GameMessage> getReciveList() {
        return reciveList;
    }

    public void sendGameMessage(GameMessage gameMessage) {
        sendList.add(gameMessage);
    }

    public void pulse() {
        time = Time2.getCurMillis();

        if (!socket.isConnected()) {
            return;
        }
        pulseSend();
        pulseRead();
    }

    private void pulseRead() {
        try {
            while (inputStream.available() > 0) {
                int len = inputStream.read(readBytes);
                GameMessage gameMessage = GameMessage.buildGameMessage(readBytes, 0, len);
                if (gameMessage != null) {
                    reciveList.add(gameMessage);
                    Log.net.info("Connection {} got {}", this.toString(), gameMessage.toString());
                }
            }
        } catch (IOException e) {
            Log.net.error("error in pulse", e);
        }
    }

    private void pulseSend() {
        try {
            while (!sendList.isEmpty()) {
                GameMessage sendMsg = sendList.poll();
                outputStream.write(sendMsg.getBytes());
                outputStream.flush();
                Log.net.info("Connection {} send {}", this.toString(), sendMsg.toString());
            }
        } catch (IOException e) {
            Log.net.error("error in pulse", e);
        }
    }

    public boolean isAlive() {
        return socket.isConnected();
    }

    @Override
    public String toString() {
        if (Utils.isNullOrEmpty(name)) {
            return String.valueOf(id);
        }
        return name + "(" + id + ")";
    }

    @Override
    public int compareTo(Delayed o) {
        SocketConnection connection = (SocketConnection) o;
        return Long.compare(time, connection.time);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time + 1000 / HZ - Time2.getCurMillis();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

}
