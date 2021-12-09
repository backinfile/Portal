package com.backinfile.portal;

import com.backinfile.gdxSupport.DefaultGame;
import com.backinfile.portal.gen.GenImage;
import com.backinfile.portal.manager.CardManager;
import com.backinfile.portal.manager.SkillManager;
import com.backinfile.portal.model.Human;
import com.backinfile.portal.model.humanOpers.SelectCardsOper;
import com.backinfile.portal.view.screens.GameScreen;
import com.backinfile.support.reflection.Reflections;

public class MainGame extends DefaultGame {
    public static MainGame Instance;

    @Override
    public void create() {
        Instance = this;

//        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
//            LogManager.setLocalAppender(ILogAppender.ConsoleAppender.Instance);
//        } else {
//            LogManager.setLocalAppender(ILogAppender.EmptyAppender.Instance);
//        }

        if (Settings.inDev()) {
            Reflections.classRewriteInit(Settings.PACKAGE_NAME, MainGame.class.getClassLoader());
            GenImage.gen();
        }

        ScreenSize.init();
        Res.init();
        SkillManager.init();
        CardManager.init();

        Human human = new Human();
        human.addHumanOper(new SelectCardsOper());

        setScreen(GameScreen.class);
    }

    @Override
    public void dispose() {
        Res.dispose();
    }
}
