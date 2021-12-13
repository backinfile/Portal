package com.backinfile.portal.model;

import com.backinfile.portal.LocalString;

public class Card {
    public long id;
    public LocalString.LocalCardString localCardString;

    public final SkillGroup skillGroup = new SkillGroup();

    public Card() {
    }

    public boolean isNumberCard() {
        return localCardString.cardType == CardType.Number;
    }

    public boolean isMonsterCard() {
        return localCardString.cardType == CardType.Monster;
    }
}
