package com.backinfile.portal.desktop;

import com.backinfile.portal.MainGame;
import com.backinfile.portal.ScreenSize;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.width = ScreenSize.SCREEN_WIDTH;
        config.height = ScreenSize.SCREEN_HEIGHT;
        new LwjglApplication(new MainGame(), config);
    }
}
