package com.backinfile.portal.model;

import com.badlogic.gdx.graphics.Color;

public enum CardType {
    Virus(Color.FIREBRICK),
    Command(Color.BLUE),
    Data(Color.LIGHT_GRAY),
    Plugin(Color.ROYAL),
    Error(Color.SCARLET),
    ;

    private final Color color;

    CardType(Color color) {
        this.color = color.cpy();
    }

    public Color getColor() {
        return color;
    }

    public boolean isMainType() {
        return this == Command || this == Data || this == Plugin;
    }
}
