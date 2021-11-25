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

    protected long duration = Settings.BASE_TIME_DURATION;
    private long curDuration;


    public Effect() {
    }

    @Override
    public final void start() {
        curDuration = duration;
    }

    @Override
    public final void update(long timeDelta) {
        if (curDuration == duration) {
            aniStart();
            if (isDone()) {
                return;
            }
            aniUpdate();
            if (isDone()) {
                return;
            }
        }

        curDuration = Math.max(0, curDuration - timeDelta);
        aniUpdate();
        if (isDone()) {
            return;
        }

        if (curDuration == 0) {
            aniEnd();
            setDone();
        }
    }

    public float getProcess() {
        return 1f - curDuration * 1f / duration;
    }

    public abstract void aniStart();

    public abstract void aniUpdate();

    public abstract void aniEnd();

    @Override
    public boolean isDone() {
        return done;
    }

    public void setDone() {
        done = true;
    }

    @Override
    public void dispose() {

    }
}
