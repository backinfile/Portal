package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.GameAction;

public class RefreshMonsterAction extends GameAction {
    public boolean isRefreshAll = false;

    public RefreshMonsterAction() {
    }

    public RefreshMonsterAction(boolean isRefreshAll) {
        this.isRefreshAll = isRefreshAll;
    }

    @Override
    public void start() {
        if (isRefreshAll) {
            if (!board.monsterShop.isEmpty()) {
                addFirst(new RefreshMonsterAction(isRefreshAll));
                addFirst(new DiscardCardAction(board.monsterShop));
                setDone();
                return;
            }
        }
        int need = board.monsterCardSlotNumber - board.monsterShop.size();
        if (board.monsterDrawPile.size() < need) {
            addFirst(new RefreshMonsterAction(isRefreshAll));
            addFirst(new ShuffleMonsterAction());
            setDone();
            return;
        }
        for (int i = 0; i < need; i++) {
            board.monsterShop.add(board.monsterDrawPile.pollTop());
            board.modify(board.monsterShop);
        }
        setDone();
    }
}
