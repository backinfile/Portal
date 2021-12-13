package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.GameAction;

public class ShuffleNumberAction extends GameAction {

    @Override
    public void start() {
        board.numberDrawPile.addAll(board.numberDiscardPile);
        board.numberDiscardPile.clear();

        board.modify(board.numberDrawPile);
        setDone();
    }
}
