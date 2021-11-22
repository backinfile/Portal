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
}
