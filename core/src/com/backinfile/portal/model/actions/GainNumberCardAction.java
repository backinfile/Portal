package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.GameAction;
import com.backinfile.portal.model.Human;

public class GainNumberCardAction extends GameAction {
    private final int position;

    public GainNumberCardAction(Human human, int position) {
        this.human = human;
        this.position = position;
    }

    @Override
    public void start() {
        if (position >= board.numberShop.size() || position < 0) {
            if (board.numberDrawPile.isEmpty()) {
                addFirst(new GainNumberCardAction(human, position));
                addFirst(new ShuffleNumberAction());
                setDone();
                return;
            }
            Card card = board.numberDrawPile.pollTop();
            human.handPile.add(card);

            board.modify(human.handPile);
        } else {
            Card card = board.numberShop.remove(position);
            human.handPile.add(card);
            board.modify(human.handPile);
            addFirst(new RefreshNumberAction());
        }
        setDone();
    }

}
