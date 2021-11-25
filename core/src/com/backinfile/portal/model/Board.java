package com.backinfile.portal.model;

import com.backinfile.support.ActionQueue;
import com.backinfile.support.IAlive;

public class Board implements IAlive {
    public Human human;
    public Source.ServerSource serverSource = new Source.ServerSource();
    public Source.VirusSource virusSource = new Source.VirusSource();
    public CardPile trashPile = new CardPile();

    private ActionQueue<GameAction> actionQueue;
    private IEffectContainer effectContainer;

    public void init(Human human, IEffectContainer effectContainer) {
        this.human = human;

        actionQueue = new ActionQueue<>(action -> {
            action.board = this;
            action.human = this.human;
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
