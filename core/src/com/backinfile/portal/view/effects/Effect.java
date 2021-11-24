package com.backinfile.portal.view.effects;

import com.backinfile.portal.Settings;
import com.backinfile.portal.model.Board;
import com.backinfile.portal.model.Human;
import com.backinfile.portal.view.group.IBoardView;
import com.backinfile.support.AutoSet;
import com.backinfile.support.IAction;

public abstract class Effect implements IAction {
    @AutoSet
    public Board board;
    @AutoSet
    public Human human;
    @AutoSet
    public IBoardView boardView;

    private boolean done = false;
    private long duration, curDuration;


    public Effect() {
        this.duration = Settings.BASE_TIME_DURATION;
    }

    @Override
    public final void start() {
        curDuration = duration;
    }

    @Override
    public final void update(long timeDelta) {
        if (curDuration == duration) {
            aniStart();
            aniUpdate();
        }

        curDuration = Math.max(0, curDuration - timeDelta);
        aniUpdate();

        if (curDuration == 0) {
            aniEnd();
            done = true;
            return;
        }


    }

    public float getProcess() {
        return curDuration * 1f / duration;
    }

    public abstract void aniStart();

    public abstract void aniUpdate();

    public abstract void aniEnd();

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void dispose() {

    }
}
