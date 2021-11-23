package com.backinfile.portal.view.actor;

import com.backinfile.portal.Res;
import com.backinfile.portal.ScreenSize;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.utils.Align;

public class AutoLabel extends AutoActor {
    private float x, y;
    private float width = 200f, height = 100f;
    private int align = Align.center;
    private boolean warp = false;
    private Color color = Color.BLACK;
    private String text = "AutoLabel";

    private final BitmapFontCache bitmapFontCache;
    private float fontScale = 1f;

    private boolean changed = false;

    public AutoLabel() {
        bitmapFontCache = Res.DefaultFont.newFontCache();
        markChanged();
    }

    public void setText(String text) {
        this.text = text;
        markChanged();
    }

    public void setFontSize(int fontSize) {
        fontScale = fontSize / ScreenSize.FONT_DEFAULT_SIZE;
        markChanged();
    }

    public void setFontScale(float fontScale) {
        this.fontScale = fontScale;
        markChanged();
    }

    public float getFontSize() {
        return ScreenSize.FONT_DEFAULT_SIZE * fontScale;
    }

    public void setBound(float x, float y, float width, float height, int align, boolean warp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.align = align;
        this.warp = warp;
        markChanged();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        if (!changed) {
            bitmapFontCache.setPosition(x, y);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }


    private void markChanged() {
        changed = true;
    }

    private void update() {
        bitmapFontCache.getFont().getData().setScale(fontScale);
        bitmapFontCache.setText(text, x, y + getFontSize() / 2f + height / 2f, width, align, warp);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (changed) {
            update();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!visible) {
            return;
        }
        bitmapFontCache.tint(color);
        bitmapFontCache.draw(batch);
    }
}
