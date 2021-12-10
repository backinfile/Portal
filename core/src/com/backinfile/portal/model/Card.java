package com.backinfile.portal.model;

import com.backinfile.portal.LocalString;

public class Card {
    public long id;
    public LocalString.LocalCardString localCardString;

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
