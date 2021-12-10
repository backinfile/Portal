package com.backinfile.portal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class Settings {
    public static final String PACKAGE_NAME = "com.backinfile";

    public static final String LOCAL_HOST = "127.0.0.1";
    public static final String BROADCAST_ADDRESS = "255.255.255.255";
    public static final int GAME_SERVER_PORT = 18081;
    public static final int GAME_CLIENT_PORT = 18082;
    public static final int GAME_SERVER_UDP_PORT = 18083;
    public static final int GAME_CLIENT_UDP_PORT = 18084;

    private static final boolean DEV = true;

    public static final long BASE_TIME_DURATION = 200;


    public static boolean inDev() {
        if (Gdx.app == null) {
            return DEV;
        }
        return DEV && Gdx.app.getType() == Application.ApplicationType.Desktop;
    }
}
