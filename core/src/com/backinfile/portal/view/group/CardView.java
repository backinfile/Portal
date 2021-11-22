package com.backinfile.portal.view.group;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Res;
import com.backinfile.portal.model.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class CardView extends Actor {

    private TextureRegionDrawable borderImage;
    private TextureRegionDrawable mainImage;
    private Color borderColor = new Color(Color.RED);
    private BitmapFontCache titleCache;

    private Card card;
    private CardViewState cardViewState;

    public CardView() {
    }

    public static class CardViewState {
        public static final CardViewState Normal = new CardViewState();

        public float x = Gdx.graphics.getWidth() / 2f;
        public float y = Gdx.graphics.getHeight() / 2f;
        public CardSize cardSize = CardSize.Normal;
    }

    public void setCard(Card card) {
        this.card = card;
        borderImage = Res.getTexture(LocalString.getUIString("card").images[0]);
        mainImage = Res.getTexture(card.localCardString.image);

        switch (card.localCardString.cardType) {
            case Virus:
                borderColor.set(Color.RED);
                break;
            case Command:
                borderColor.set(Color.BLUE);
                break;
            case Data:
                borderColor.set(Color.GREEN);
                break;
        }

        setCardViewState(CardViewState.Normal);
    }

    public void setCardViewState(CardViewState cardViewState) {
        this.cardViewState = cardViewState;
        setSize(cardViewState.cardSize.getWidth(), cardViewState.cardSize.getHeight());
        setOrigin(Align.center);
        setPosition(cardViewState.x, cardViewState.y, Align.center);

        BitmapFont newFont = Res.getNearBitmapFont(cardViewState.cardSize);
        if (titleCache == null || titleCache.getFont() != newFont) {
            titleCache = newFont.newFontCache();
        }
        titleCache.setText(card.localCardString.name, getWidth() * 0.2f, cardViewState.cardSize.getFontHeight() * 1.1f, getWidth() * 0.8f, Align.center, false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (card == null) {
            return;
        }
        batch.setColor(getColor());
        mainImage.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        batch.setColor(borderColor);
        borderImage.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        titleCache.tint(Color.BLACK);
        titleCache.setPosition(getX(), getY());
        titleCache.draw(batch);
    }
}
