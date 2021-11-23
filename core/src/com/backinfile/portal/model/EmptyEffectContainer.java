package com.backinfile.portal.model;

public class EmptyEffectContainer implements IEffectContainer {
    @Override
    public void add(Effect effect) {

    }

    @Override
    public void flush() {

    }

    @Override
    public boolean isOver() {
        return true;
    }

    @Override
    public void clear() {

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
}
