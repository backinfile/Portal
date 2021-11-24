package com.backinfile.portal.model;

import com.backinfile.portal.view.effects.Effect;
import com.backinfile.support.AutoSet;
import com.backinfile.support.IAction;

public abstract class GameAction implements IAction {
    private boolean done = false;

    @AutoSet
    public Board board;
    @AutoSet
    public Human human;

    public Card card;
    public int number;

    @Override
    public boolean isDone() {
        return done;
    }

    public void setDone() {
        this.done = true;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(long timeDelta) {

    }

    @Override
    public void dispose() {

    }

    public final void addLast(GameAction action) {
        board.getActionQueue().addLast(action);
    }

    public final void addFirst(GameAction action) {
        board.getActionQueue().addFirst(action);
    }

    public final void addEffect(Effect effect) {
        board.getEffectContainer().add(effect);
    }

    public final void flushEffect() {
        board.getEffectContainer().flush();
    }

    public final void addAndFlush(Effect effect) {
        board.getEffectContainer().addAndFlush(effect);
    }
}
