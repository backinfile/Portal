package com.backinfile.portal.model;

import com.backinfile.support.IAction;

public abstract class Effect implements IAction {
    private boolean done = false;
    private long duration, curDuration;

    public Effect(long duration) {
        this.duration = duration;
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
