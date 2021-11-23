package com.backinfile.portal.view.actor;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.List;

public class AutoGroup extends AutoActor {
    private List<AutoActor> actorList = new ArrayList<>();

    public void addActor(AutoActor autoActor) {
        this.actorList.add(autoActor);
    }

    public boolean removeActor(AutoActor autoActor) {
        return this.actorList.remove(autoActor);
    }

    public void clearActor() {
        this.actorList.clear();
    }

    @Override
    public void act(float delta) {
        for (AutoActor autoActor : actorList) {
            autoActor.act(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!visible) {
            return;
        }
        for (AutoActor autoActor : actorList) {
            autoActor.draw(batch, parentAlpha);
        }
    }
}
