package com.backinfile.portal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class Settings {
    public static final String PACKAGE_NAME = "com.backinfile";

    private static final boolean DEV = true;


    public static boolean inDev() {
        if (Gdx.app == null) {
            return false;
        }
        return DEV && Gdx.app.getType() == Application.ApplicationType.Desktop;
    }
}
