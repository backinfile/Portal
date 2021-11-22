package com.backinfile.portal;

import com.badlogic.gdx.Gdx;

public class GlobalSize {
    public static float STAGE_WIDTH;
    public static float STAGE_HEIGHT;

    public static float CARD_WIDTH;
    public static float CARD_HEIGHT;
    public static float CARD_WIDTH_L;
    public static float CARD_HEIGHT_L;
    public static float CARD_WIDTH_LL;
    public static float CARD_HEIGHT_LL;
    public static float CARD_WIDTH_S;
    public static float CARD_HEIGHT_S;

    public static void init() {
        STAGE_HEIGHT = Gdx.graphics.getHeight();
        STAGE_WIDTH = STAGE_HEIGHT / 0.8f;

        CARD_HEIGHT = Gdx.graphics.getHeight() * 0.15f;
        CARD_WIDTH = CARD_HEIGHT * 0.715f;
        CARD_HEIGHT_L = CARD_HEIGHT * 4 / 3f;
        CARD_WIDTH_L = CARD_WIDTH * 4 / 3f;
        CARD_HEIGHT_LL = CARD_HEIGHT * 3.6f;
        CARD_WIDTH_LL = CARD_WIDTH * 3.6f;
        CARD_HEIGHT_S = CARD_HEIGHT * 3f / 4f;
        CARD_WIDTH_S = CARD_WIDTH * 3f / 4f;
    }

}
