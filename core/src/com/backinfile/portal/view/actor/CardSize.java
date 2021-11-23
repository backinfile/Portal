package com.backinfile.portal.view.actor;

import com.backinfile.portal.ScreenSize;

public enum CardSize {
    Small,
    Normal,
    Large,
    LargeLarge,
    ;

    public float getWidth() {
        switch (this) {
            case Normal:
                return ScreenSize.CARD_WIDTH;
            case Small:
                return ScreenSize.CARD_WIDTH_S;
            case Large:
                return ScreenSize.CARD_WIDTH_L;
            case LargeLarge:
                return ScreenSize.CARD_WIDTH_LL;
        }
        return ScreenSize.CARD_WIDTH;
    }

    public float getHeight() {
        switch (this) {
            case Normal:
                return ScreenSize.CARD_HEIGHT;
            case Small:
                return ScreenSize.CARD_HEIGHT_S;
            case Large:
                return ScreenSize.CARD_HEIGHT_L;
            case LargeLarge:
                return ScreenSize.CARD_HEIGHT_LL;
        }
        return ScreenSize.CARD_HEIGHT;
    }

    public float getScale() {
        switch (this) {
            case Normal:
                return ScreenSize.CARD_NORMAL_SCALE;
            case Small:
                return ScreenSize.CARD_SMALL_SCALE;
            case Large:
                return ScreenSize.CARD_LARGE_SCALE;
            case LargeLarge:
                return ScreenSize.CARD_LARGE_LARGE_SCALE;
        }
        return ScreenSize.CARD_NORMAL_SCALE;
    }

    public float getFontSize() {
        switch (this) {
            case Normal:
                return ScreenSize.CARD_NORMAL_SCALE * ScreenSize.FONT_DEFAULT_SIZE;
            case Small:
                return ScreenSize.CARD_SMALL_SCALE * ScreenSize.FONT_DEFAULT_SIZE;
            case Large:
                return ScreenSize.CARD_LARGE_SCALE * ScreenSize.FONT_DEFAULT_SIZE;
            case LargeLarge:
                return ScreenSize.CARD_LARGE_LARGE_SCALE * ScreenSize.FONT_DEFAULT_SIZE;
        }
        return ScreenSize.FONT_DEFAULT_SIZE;
    }

    public float getDecorateSize() {
        switch (this) {
            case Normal:
                return ScreenSize.SCREEN_CARD_DECORATE_SIZE * ScreenSize.CARD_WIDTH / ScreenSize.SCREEN_CARD_WIDTH;
            case Small:
                return ScreenSize.SCREEN_CARD_DECORATE_SIZE * ScreenSize.CARD_WIDTH_S / ScreenSize.SCREEN_CARD_WIDTH;
            case Large:
                return ScreenSize.SCREEN_CARD_DECORATE_SIZE * ScreenSize.CARD_WIDTH_L / ScreenSize.SCREEN_CARD_WIDTH;
            case LargeLarge:
                return ScreenSize.SCREEN_CARD_DECORATE_SIZE * ScreenSize.CARD_WIDTH_LL / ScreenSize.SCREEN_CARD_WIDTH;
        }
        return ScreenSize.SCREEN_CARD_DECORATE_SIZE * ScreenSize.CARD_WIDTH / ScreenSize.SCREEN_CARD_WIDTH;
    }

    public CardSize getNext() {
        return values()[Math.min(values().length - 1, ordinal() + 1)];
    }
}
