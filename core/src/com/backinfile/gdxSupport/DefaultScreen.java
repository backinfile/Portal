package com.backinfile.gdxSupport;

import com.badlogic.gdx.ScreenAdapter;

/**
 * 需求无参构造函数
 */
public abstract class DefaultScreen extends ScreenAdapter {
    DefaultGame game;
    private float timeToFixRender = 0f;

    @Override
    public void render(float delta) {
        super.render(delta);

        timeToFixRender += delta;
        while (timeToFixRender >= DefaultGame.TimePerFrame) {
            timeToFixRender -= DefaultGame.TimePerFrame;
            fixRender();
        }
    }

    public void fixRender() {
    }

    public DefaultGame getGame() {
        return game;
    }
}
