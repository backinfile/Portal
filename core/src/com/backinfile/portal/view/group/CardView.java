package com.backinfile.portal.view.group;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Res;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.CardType;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.support.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class CardView extends Group {
    public static final LocalString.LocalUIString uiString = LocalString.getUIString("card");


    private Card card;
    private CardViewState cardViewState;
    private Integer health = null;

    private Label titleLabel = new Label("", new Label.LabelStyle(Res.DefaultFont, Color.BLACK));
    private Label healthLabel = new Label("", new Label.LabelStyle(Res.DefaultFont, Color.BLACK));
    private Image mainImage = new Image();
    private Image borderImage = new Image();

    public CardView() {
        addActor(mainImage);
        addActor(borderImage);
        addActor(healthLabel);
        addActor(titleLabel);
    }

    public static class CardViewState {
        public static final CardViewState Normal = new CardViewState();

        public float x = Gdx.graphics.getWidth() / 2f;
        public float y = Gdx.graphics.getHeight() / 2f;
        public CardSize cardSize = CardSize.Normal;
    }

    public void setCard(Card card) {
        this.card = card;
        borderImage.setDrawable(Res.getTexture(uiString.images[0]));
        mainImage.setDrawable(Res.getTexture(card.localCardString.image));

        switch (card.localCardString.cardType) {
            case Virus: {
                borderImage.setColor(Color.RED);
                break;
            }
            case Command: {
                borderImage.setColor(Color.BLUE);
                break;
            }
            case Data: {
                borderImage.setColor(Color.GREEN);
                break;
            }
        }

        setCardViewState(CardViewState.Normal);
    }

    public void setCardViewState(CardViewState cardViewState) {
        this.cardViewState = cardViewState;
        setSize(cardViewState.cardSize.getWidth(), cardViewState.cardSize.getHeight());
        setOrigin(Align.center);
        setPosition(cardViewState.x, cardViewState.y, Align.center);

        borderImage.setBounds(0, 0, getWidth(), getHeight());
        mainImage.setBounds(0, 0, getWidth(), getHeight());

        setTitle(card.localCardString.name);

        if (card.localCardString.cardType == CardType.Virus) {
            setHealth(card.finalHealth);
        } else {
            setHealth(null);
        }
    }

    public void setTitle(String text) {
        if (Utils.isNullOrEmpty(text)) {
            titleLabel.setVisible(false);
        } else {
            titleLabel.setFontScale(cardViewState.cardSize.getScale());
            titleLabel.setAlignment(Align.center);
            titleLabel.setWrap(false);
            titleLabel.setBounds(0, getHeight() * 0.02f, getWidth(), cardViewState.cardSize.getFontSize());
            titleLabel.setText(text);
            titleLabel.setVisible(true);
        }
    }

    public void setHealth(Integer healthPoint) {
        this.health = healthPoint;
        if (health != null) {
            health = Math.max(0, health);
            CardSize cardSize = cardViewState.cardSize.getNext();
            healthLabel.setFontScale(cardSize.getScale());
            healthLabel.setAlignment(Align.left);
            healthLabel.setWrap(false);
            healthLabel.setBounds(getWidth() * 0.1f, getHeight() * 0.9f - cardSize.getFontSize(), getWidth(), cardSize.getFontSize());
            healthLabel.setText(health.toString());
            healthLabel.setVisible(true);
        } else {
            healthLabel.setVisible(false);
        }
    }

    private float getHealthDecorateOffsetX() {
        return cardViewState.cardSize.getDecorateSize() * 0.2f;
    }

    private float getHealthDecorateOffsetY() {
        return getHeight();
    }
}
