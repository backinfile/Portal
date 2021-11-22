package com.backinfile.portal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class Settings {
    public static final String PACKAGE_NAME = "com.backinfile";

    private static final boolean DEV = true;

    public static final int SCREEN_WIDTH = 1334;
    public static final int SCREEN_HEIGHT = 750;

    public static final int CARD_WIDTH = 600;
    public static final int CARD_HEIGHT = 800;

    public static boolean inDev() {
        if (Gdx.app == null) {
            return false;
        }
        return DEV && Gdx.app.getType() == Application.ApplicationType.Desktop;
    }
}
