package com.backinfile.portal.model;

public enum CardType {
    Virus(1),
    Command(2),
    Data(3),
    ;

    private int type;

    private CardType(int type) {
        this.type = type;
    }
}
