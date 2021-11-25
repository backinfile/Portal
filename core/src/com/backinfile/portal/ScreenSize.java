package com.backinfile.portal;

import com.badlogic.gdx.Gdx;

public class ScreenSize {
    public static final int SCREEN_WIDTH = 1334;
    public static final int SCREEN_HEIGHT = 750;

    public static final int SCREEN_CARD_WIDTH = 630;
    public static final int SCREEN_CARD_HEIGHT = 880;

    public static final int SCREEN_CARD_DECORATE_SIZE = SCREEN_CARD_WIDTH / 6;


    public static final float CARD_BOARD_WIDTH_RATE = 1 / 30f;


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

    public static final float CARD_SMALL_SCALE = 0.3f;
    public static final float CARD_NORMAL_SCALE = 0.5f;
    public static final float CARD_LARGE_SCALE = 1f;
    public static final float CARD_LARGE_LARGE_SCALE = 1.2f;

    public static final float FONT_DEFAULT_SIZE = 36 * 2f;


    public static void init() {
        STAGE_HEIGHT = Gdx.graphics.getHeight();
        STAGE_WIDTH = Gdx.graphics.getWidth();

        float height = Gdx.graphics.getHeight() * 0.8f;
        float width = height * SCREEN_CARD_WIDTH / SCREEN_CARD_HEIGHT;

        CARD_HEIGHT_S = height * CARD_SMALL_SCALE;
        CARD_WIDTH_S = width * CARD_SMALL_SCALE;
        CARD_HEIGHT = height * CARD_NORMAL_SCALE;
        CARD_WIDTH = width * CARD_NORMAL_SCALE;
        CARD_HEIGHT_L = height * CARD_LARGE_SCALE;
        CARD_WIDTH_L = width * CARD_LARGE_SCALE;
        CARD_HEIGHT_LL = height * CARD_LARGE_LARGE_SCALE;
        CARD_WIDTH_LL = width * CARD_LARGE_LARGE_SCALE;
    }

}
