package com.backinfile.portal.model.actions;

import com.backinfile.portal.model.GameAction;
import com.backinfile.portal.view.effects.Effect;

public class EffectAction extends GameAction {
    private Effect[] effects;

    public EffectAction(Effect... effects) {
        this.effects = effects;
    }

    @Override
    public void start() {
        for (Effect effect : effects) {
            addEffect(effect);
        }
        flushEffect();
    }

    @Override
    public void update(long timeDelta) {
        if (board.getEffectContainer().isOver()) {
            setDone();
        }
    }
}
