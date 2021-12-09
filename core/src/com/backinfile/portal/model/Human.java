package com.backinfile.portal.model;

import com.backinfile.support.AutoSet;

public class Human {
    @AutoSet
    public Board board;


    public int actionPoint = 0;
    public int winPoint = 0;
    public int diamond = 0;

    public CardPile handPile = new CardPile();
    public CardPile slotPile = new CardPile();
    public CardPile monsterPile = new CardPile();

    public Human() {
    }

    public void init() {

    }


    public void onTurnStart() {
    }

    public void onTurnEnd() {
    }
}
