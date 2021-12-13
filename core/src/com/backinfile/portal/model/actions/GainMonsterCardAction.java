package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.GameAction;
import com.backinfile.portal.model.Human;
import com.backinfile.portal.model.humanOpers.SelectCardsOper;

public class GainMonsterCardAction extends GameAction {
    private final int position;

    public GainMonsterCardAction(Human human, int position) {
        this.human = human;
        this.position = position;
    }

    @Override
    public void start() {
        if (position >= board.monsterShop.size() || position < 0) {
            if (board.monsterDrawPile.isEmpty()) {
                addFirst(new GainMonsterCardAction(human, position));
                addFirst(new ShuffleMonsterAction());
                setDone();
                return;
            }
            Card card = board.monsterDrawPile.pollTop();
            addToGate(card);
        } else {
            Card card = board.monsterShop.remove(position);
            addLast(new RefreshMonsterAction());
            addToGate(card);
        }
    }

    private void addToGate(Card card) {
        if (human.gatePile.size() < human.gateSlotNumber) {
            human.gatePile.add(card);
            board.modify(human.gatePile);
        } else {
            SelectCardsOper humanOper = new SelectCardsOper(human.gatePile, 1, 1, "select one to discard");
            humanOper.setDetachCallback(() -> {
                if (!humanOper.getSelected().isEmpty()) {
                    board.removeCard(humanOper.getSelected().getAny());
                }
                finallyAddToGate(card);
            });
            human.addHumanOper(humanOper);
        }
    }

    private void finallyAddToGate(Card card) {
        if (human.gatePile.size() < human.gateSlotNumber) {
            human.gatePile.add(card);
            board.modify(human.gatePile);
        } else {
            
        }
    }
}
