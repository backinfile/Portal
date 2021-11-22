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

    public static final float CARD_NORMAL_SCALE = 1f;
    public static final float CARD_SMALL_SCALE = 0.75f;
    public static final float CARD_LARGE_SCALE = 1.33f;
    public static final float CARD_LARGE_LARGE_SCALE = 2.2f;

    public static final float FONT_DEFAULT_SIZE = 36;

    public static void init() {
        STAGE_HEIGHT = Gdx.graphics.getHeight();
        STAGE_WIDTH = STAGE_HEIGHT / 0.8f;

        CARD_HEIGHT = Gdx.graphics.getHeight() * 0.3f;
        CARD_WIDTH = CARD_HEIGHT / 8 * 6;
        CARD_HEIGHT_S = CARD_HEIGHT * CARD_SMALL_SCALE;
        CARD_WIDTH_S = CARD_WIDTH * CARD_SMALL_SCALE;
        CARD_HEIGHT_L = CARD_HEIGHT * CARD_LARGE_SCALE;
        CARD_WIDTH_L = CARD_WIDTH * CARD_LARGE_SCALE;
        CARD_HEIGHT_LL = CARD_HEIGHT * CARD_LARGE_LARGE_SCALE;
        CARD_WIDTH_LL = CARD_WIDTH * CARD_LARGE_LARGE_SCALE;
    }

}
