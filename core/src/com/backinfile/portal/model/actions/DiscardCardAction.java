package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.CardPile;
import com.backinfile.portal.model.GameAction;

import java.util.Arrays;

public class DiscardCardAction extends GameAction {
    private final CardPile cardPile = new CardPile();

    public DiscardCardAction(CardPile cardPile) {
        this.cardPile.addAll(cardPile);
    }

    public DiscardCardAction(CardPile... cardPiles) {
        for (CardPile cardPile : cardPiles) {
            this.cardPile.addAll(cardPile);
        }
    }

    public DiscardCardAction(Card... cards) {
        this.cardPile.addAll(Arrays.asList(cards));
    }

    @Override
    public void start() {
        boolean hasNumber = this.cardPile.stream().anyMatch(Card::isNumberCard);
        boolean hasMonster = this.cardPile.stream().anyMatch(Card::isMonsterCard);

        for (Card card : cardPile) {
            if (card.isNumberCard()) {
                board.numberDiscardPile.add(card);
            } else {
                board.monsterDiscardPile.add(card);
            }
        }
        board.modify(cardPile);
        setDone();
    }
}
