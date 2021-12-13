package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.GameAction;

public class RefreshNumberAction extends GameAction {
    private boolean isRefreshAll = false;

    public RefreshNumberAction() {
    }

    public RefreshNumberAction(boolean isRefreshAll) {
        this.isRefreshAll = isRefreshAll;
    }

    @Override
    public void start() {
        if (isRefreshAll) {
            if (!board.numberShop.isEmpty()) {
                addFirst(new RefreshNumberAction(isRefreshAll));
                addFirst(new DiscardCardAction(board.numberShop));
                setDone();
                return;
            }
        }

        int need = board.numberCardSlotNumber - board.numberShop.size();

        if (board.numberDrawPile.size() < need) {
            addFirst(new RefreshNumberAction());
            addFirst(new ShuffleNumberAction());
            setDone();
            return;
        }

        while (board.numberShop.size() < board.numberCardSlotNumber) {
            Card card = board.numberDrawPile.pollTop();
            board.numberShop.add(card);
            board.modify(board.numberShop);

            if (card.localCardString.refreshMonster) {
                addFirst(new RefreshNumberAction());
                addFirst(new RefreshMonsterAction(true));
                setDone();
                return;
            }
        }

        setDone();
    }
}
