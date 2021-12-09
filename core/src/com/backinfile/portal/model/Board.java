package com.backinfile.portal.model;

import com.backinfile.support.ActionQueue;
import com.backinfile.support.IAlive;

import java.util.ArrayList;
import java.util.List;

public class Board implements IAlive {

    public final CardPile monsterPile = new CardPile();
    public final CardPile monsterShop = new CardPile();
    public final CardPile numberPile = new CardPile();
    public final CardPile numberShop = new CardPile();

    public int monsterSlotNumber = 4;
    public int numberSlotNumber = 5;


    public final List<Human> humanList = new ArrayList<>();
    
    private ActionQueue<GameAction> actionQueue;
    private IEffectContainer effectContainer;

    public void init(IEffectContainer effectContainer) {
        actionQueue = new ActionQueue<>(action -> {
            action.board = this;
        });

        this.effectContainer = effectContainer;
    }

    @Override
    public void start() {
    }

    @Override
    public void update(long timeDelta) {
        actionQueue.update(timeDelta);
        effectContainer.flush();
        effectContainer.update(timeDelta);
    }

    @Override
    public void dispose() {
        actionQueue.clear();
        effectContainer.clear();

    }

    public ActionQueue<GameAction> getActionQueue() {
        return actionQueue;
    }

    public IEffectContainer getEffectContainer() {
        return effectContainer;
    }
}
