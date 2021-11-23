package com.backinfile.portal.model;

import com.backinfile.support.ActionQueue;
import com.backinfile.support.IAlive;

public class Board implements IAlive {
    public Human human;
    public Source.ServerSource serverSource;
    public Source.VirusSource virusSource;

    private ActionQueue<GameAction> actionQueue;
    private IEffectContainer effectContainer;

    public void init(IEffectContainer effectContainer) {
        actionQueue = new ActionQueue<>(action -> {
            action.board = this;
            action.human = human;
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

    }

    public void addFirst(GameAction gameAction) {
        actionQueue.addFirst(gameAction);
    }

    public void addLast(GameAction gameAction) {
        actionQueue.addLast(gameAction);
    }
}
