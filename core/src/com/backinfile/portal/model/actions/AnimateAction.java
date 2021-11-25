package com.backinfile.portal.model.actions;

import com.backinfile.portal.Settings;
import com.backinfile.portal.model.GameAction;

public abstract class AnimateAction extends GameAction {
    protected long duration = Settings.BASE_TIME_DURATION;
    private long curDuration = 0;

    @Override
    public void start() {
        curDuration = duration;
    }

    @Override
    public void update(long timeDelta) {
        super.update(timeDelta);

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

        if (curDuration <= 0) {
            aniEnd();
            setDone();
        }

    }

    public float getProcess() {
        return 1 - curDuration * 1f / duration;
    }

    public abstract void aniStart();

    public abstract void aniUpdate();

    public abstract void aniEnd();
}
