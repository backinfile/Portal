package com.backinfile.portal.model;

import com.backinfile.support.func.Action1;

import java.util.Iterator;
import java.util.LinkedList;

public class EffectContainer implements IEffectContainer {
    private static final LinkedList<Effect> EmptyEffectList = new LinkedList<>();

    private final LinkedList<LinkedList<Effect>> effectWaitingList = new LinkedList<>();
    private LinkedList<Effect> curEffectList = null;
    private LinkedList<Effect> addingEffectList = null;

    private Action1<Effect> prepare = null;

    public EffectContainer() {
    }

    public EffectContainer(Action1<Effect> prepare) {
        this.prepare = prepare;
    }

    @Override
    public void add(Effect effect) {
        if (addingEffectList == null) {
            addingEffectList = new LinkedList<>();
        }
        addingEffectList.add(effect);
    }

    @Override
    public void flush() {
        if (addingEffectList != null && !addingEffectList.isEmpty()) {
            effectWaitingList.add(addingEffectList);
            addingEffectList = null;
        }
    }

    @Override
    public boolean isOver() {
        return (curEffectList == null || curEffectList.isEmpty()) && effectWaitingList.isEmpty();
    }

    @Override
    public void clear() {
        effectWaitingList.clear();
        curEffectList = null;
        addingEffectList = null;
    }

    @Override
    public void start() {
        clear();
    }

    @Override
    public void update(long timeDelta) {
        // init
        if (curEffectList == null) {
            if (effectWaitingList.isEmpty()) {
                return;
            }
            do {
                curEffectList = effectWaitingList.pollFirst();
            } while (curEffectList != null && curEffectList.isEmpty());

            if (curEffectList != null) {
                for (Iterator<Effect> iterator = curEffectList.iterator(); iterator.hasNext(); ) {
                    Effect effect = iterator.next();
                    if (prepare != null) {
                        prepare.invoke(effect);
                    }
                    effect.start();
                    if (effect.isDone()) {
                        effect.dispose();
                        iterator.remove();
                    }
                }
            }
        }

        // update
        if (curEffectList != null) {
            for (Iterator<Effect> iterator = curEffectList.iterator(); iterator.hasNext(); ) {
                Effect effect = iterator.next();
                effect.update(timeDelta);
                if (effect.isDone()) {
                    effect.dispose();
                    iterator.remove();
                }
            }
            if (curEffectList.isEmpty()) {
                curEffectList = null;
            }
        }
    }

    @Override
    public void dispose() {
        clear();
    }
}
