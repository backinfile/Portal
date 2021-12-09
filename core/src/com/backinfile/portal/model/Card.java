package com.backinfile.portal.model;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.model.skills.SkillGroup;

public class Card {
    public long id;
    public LocalString.LocalCardString localCardString;
    public CardType cardType = CardType.Monster;

    public final SkillGroup skillGroup = new SkillGroup();

    public Card() {
    }


    public static class NumberCard extends Card {
        private final int number;

        public NumberCard(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }
}
