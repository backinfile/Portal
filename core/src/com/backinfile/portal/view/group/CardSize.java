package com.backinfile.portal.view.group;

import com.backinfile.portal.GlobalSize;

public enum CardSize {
    Normal,
    Small,
    Large,
    LargeLarge,
    ;

    public float getWidth() {
        switch (this) {
            case Normal:
                return GlobalSize.CARD_WIDTH;
            case Small:
                return GlobalSize.CARD_WIDTH_S;
            case Large:
                return GlobalSize.CARD_WIDTH_L;
            case LargeLarge:
                return GlobalSize.CARD_WIDTH_LL;
        }
        return GlobalSize.CARD_WIDTH;
    }

    public float getHeight() {
        switch (this) {
            case Normal:
                return GlobalSize.CARD_HEIGHT;
            case Small:
                return GlobalSize.CARD_HEIGHT_S;
            case Large:
                return GlobalSize.CARD_HEIGHT_L;
            case LargeLarge:
                return GlobalSize.CARD_HEIGHT_LL;
        }
        return GlobalSize.CARD_HEIGHT;
    }

    public float getScale() {
        switch (this) {
            case Normal:
                return GlobalSize.CARD_NORMAL_SCALE;
            case Small:
                return GlobalSize.CARD_SMALL_SCALE;
            case Large:
                return GlobalSize.CARD_LARGE_SCALE;
            case LargeLarge:
                return GlobalSize.CARD_LARGE_LARGE_SCALE;
        }
        return GlobalSize.CARD_NORMAL_SCALE;
    }

    public float getFontHeight() {
        switch (this) {
            case Normal:
                return GlobalSize.CARD_NORMAL_SCALE * GlobalSize.FONT_DEFAULT_SIZE;
            case Small:
                return GlobalSize.CARD_SMALL_SCALE * GlobalSize.FONT_DEFAULT_SIZE;
            case Large:
                return GlobalSize.CARD_LARGE_SCALE * GlobalSize.FONT_DEFAULT_SIZE;
            case LargeLarge:
                return GlobalSize.CARD_LARGE_LARGE_SCALE * GlobalSize.FONT_DEFAULT_SIZE;
        }
        return GlobalSize.FONT_DEFAULT_SIZE;
    }
}
