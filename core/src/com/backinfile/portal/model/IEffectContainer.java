package com.backinfile.portal.model;

import com.backinfile.support.IAlive;

public interface IEffectContainer extends IAlive {
    void add(Effect effect);

    void flush();

    default void addAndFlush(Effect effect) {
        add(effect);
        flush();
    }

    boolean isOver();

    void clear();
}
