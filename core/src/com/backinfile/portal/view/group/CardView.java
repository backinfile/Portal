package com.backinfile.portal.view.group;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Res;
import com.backinfile.portal.model.Card;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class CardView extends Actor {

    private TextureRegionDrawable borderImage;
    private TextureRegionDrawable mainImage;

    private final GlyphLayout titleLayout = new GlyphLayout();

    private Card card;

    public CardView() {
    }

    public static class CardViewState {
        public static final CardViewState Normal = new CardViewState();
        public CardSize cardSize = CardSize.Normal;
    }

    public void setCard(Card card) {
        this.card = card;
        borderImage = Res.getTexture(LocalString.getUIString("card").images[0]);
        mainImage = Res.getTexture(card.localCardString.image);

        setCardViewState(CardViewState.Normal);
    }

    public void setCardViewState(CardViewState cardViewState) {
        setSize(cardViewState.cardSize.getWidth(), cardViewState.cardSize.getHeight());
        setOrigin(Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (card == null) {
            return;
        }
        mainImage.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        borderImage.draw(batch, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
