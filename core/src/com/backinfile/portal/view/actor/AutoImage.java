package com.backinfile.portal.view.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AutoImage extends AutoActor {

    private Sprite sprite;

    public AutoImage() {
        sprite = new Sprite();
    }

    public void setTexture(TextureRegion region) {
        sprite.setRegion(region);
        sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
        sprite.setColor(1, 1, 1, 1);
    }

    public void setBounds(float x, float y, float width, float height) {
        sprite.setBounds(x, y, width, height);
    }

    public void setColor(Color color) {
        sprite.setColor(color);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!visible) {
            return;
        }
        sprite.draw(batch, parentAlpha);
    }
}
