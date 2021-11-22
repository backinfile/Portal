package com.backinfile.portal.view.screens;

import com.backinfile.gdxSupport.DefaultScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends DefaultScreen {

    private GameStage gameStage;

    @Override
    public void show() {
        if (gameStage == null) {
            gameStage = new GameStage();
        }
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.8f, 0.8f, 0.8f, 1);

        if (gameStage != null) {
            gameStage.act(delta);

            gameStage.draw();
        }
    }
}
