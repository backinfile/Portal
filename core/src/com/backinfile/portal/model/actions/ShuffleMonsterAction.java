package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.GameAction;

public class ShuffleMonsterAction extends GameAction {
    @Override
    public void start() {
        board.monsterDrawPile.addAll(board.monsterDiscardPile);
        board.monsterDiscardPile.clear();

        board.modify(board.monsterDrawPile);
        setDone();
    }
}
