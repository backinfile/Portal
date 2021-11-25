package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.CardPile;
import com.backinfile.portal.model.GameAction;
import com.backinfile.portal.model.effects.RefreshHandLayout;

public class DrawCardAction extends GameAction {

    public DrawCardAction(int number) {
        this.number = number;
    }

    @Override
    public void start() {
        int drawPileSize = human.drawPile.size();
        int discardPileSize = human.discardPile.size();

        if (drawPileSize < number) {
            if (discardPileSize == 0) {
                number = drawPileSize;
            } else {
                addFirst(new DrawCardAction(number - drawPileSize));
                addFirst(new ShuffleDiscardPileToDrawPileAction());
                addFirst(new DrawCardAction(drawPileSize));
                setDone();
                return;
            }
        }

        CardPile drawn = human.drawPile.pollTop(drawPileSize);
        human.handPile.addAll(drawn);
        addAndFlush(new RefreshHandLayout());
    }

    @Override
    public void update(long timeDelta) {
        if (board.getEffectContainer().isOver()) {
            setDone();
        }
    }
}
