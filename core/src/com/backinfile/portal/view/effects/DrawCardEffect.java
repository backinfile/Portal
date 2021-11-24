package com.backinfile.portal.view.effects;

import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.group.CardView;

public class DrawCardEffect extends Effect {
    private Card card;
    private CardView cardView;

    public DrawCardEffect(Card card, int handSize, int handPosition) {
        
    }

    @Override
    public void aniStart() {
        card = human.drawPile.pollTop();
        human.handPile.add(card);
    }

    @Override
    public void aniUpdate() {

    }

    @Override
    public void aniEnd() {

    }
}
