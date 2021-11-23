package com.backinfile.portal;

import com.backinfile.gdxSupport.DefaultGame;
import com.backinfile.portal.gen.GenImage;
import com.backinfile.portal.view.screens.GameScreen;
import com.backinfile.support.log.ILogAppender;
import com.backinfile.support.log.LogManager;
import com.backinfile.support.reflection.Reflections;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class MainGame extends DefaultGame {
    public static MainGame Instance;

    @Override
    public void create() {
        Instance = this;

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            LogManager.setLocalAppender(ILogAppender.ConsoleAppender.Instance);
        } else {
            LogManager.setLocalAppender(ILogAppender.EmptyAppender.Instance);
        }

        if (Settings.inDev()) {
            Reflections.classRewriteInit(Settings.PACKAGE_NAME, MainGame.class.getClassLoader());
            GenImage.gen();
        }

        ScreenSize.init();
        Res.init();

        setScreen(GameScreen.class);
    }

    @Override
    public void dispose() {
        Res.dispose();
    }
}
