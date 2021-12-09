package com.backinfile.portal.model;

import com.badlogic.gdx.graphics.Color;

public enum CardType {
    Number(Color.ROYAL),
    Monster(Color.FIREBRICK),
    ;

    private final Color color;

    CardType(Color color) {
        this.color = color.cpy();
    }

    public Color getColor() {
        return color;
    }

}
