package com.backinfile.portal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class Settings {
    public static final String PACKAGE_NAME = "com.backinfile";

    public static final boolean DEV = true;

    public static final int CARD_WIDTH = 600;
    public static final int CARD_HEIGHT = 800;

    public static boolean inDev() {
        if (Gdx.app == null) {
            return DEV;
        }
        return DEV && Gdx.app.getType() == Application.ApplicationType.Desktop;
    }
}
