package com.backinfile.portal.model;

import com.backinfile.support.AutoSet;
import com.backinfile.support.IAction;

public abstract class GameAction implements IAction {
    private boolean done = false;

    @AutoSet
    public Board board;

    public Human human;
    public Card card;
    public int number;
    public int position;

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

}
