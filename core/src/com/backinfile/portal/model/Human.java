package com.backinfile.portal.model;

import com.backinfile.portal.model.actions.DiscardAllHandAction;
import com.backinfile.portal.model.actions.DrawCardAction;
import com.backinfile.support.AutoSet;

public class Human {
    @AutoSet
    public Board board;

    public int curEnergy = 0;
    public int curCombat = 0;

    public CardPile drawPile;
    public CardPile discardPile;
    public CardPile handPile;


    public void onTurnStart() {

    }

    public void onTurnEnd() {
        board.getActionQueue().addLast(new DiscardAllHandAction());
        board.getActionQueue().addLast(new DrawCardAction(5));
    }
}
