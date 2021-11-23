package com.backinfile.portal.view.actor;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Res;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.CardType;
import com.backinfile.support.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class CardView extends Actor {
    public static final LocalString.LocalUIString uiString = LocalString.getUIString("card");


    private Card card;
    private CardViewState cardViewState;

    private Integer health = null;


    private final AutoGroup autoGroup = new AutoGroup();
    private final AutoLabel titleText = new AutoLabel();
    private AutoLabel healthText = new AutoLabel();
    private AutoImage mainImage = new AutoImage();
    private AutoImage borderImage = new AutoImage();

    public CardView() {
        mainImage.setVisible(false);
        borderImage.setVisible(false);
        titleText.setVisible(false);
        healthText.setVisible(false);
        autoGroup.addActor(mainImage);
        autoGroup.addActor(borderImage);
        autoGroup.addActor(titleText);
        autoGroup.addActor(healthText);
    }

    public static class CardViewState {
        public static final CardViewState Normal = new CardViewState();

        public float x = Gdx.graphics.getWidth() / 2f;
        public float y = Gdx.graphics.getHeight() / 2f;
        public CardSize cardSize = CardSize.Normal;
    }

    public void setCard(Card card) {
        this.card = card;
        borderImage.setTexture(Res.newSprite(uiString.images[0]));
        borderImage.setVisible(true);
        mainImage.setTexture(Res.newSprite(card.localCardString.image));
        mainImage.setVisible(true);

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

        borderImage.setBounds(getX(), getY(), getWidth(), getHeight());
        mainImage.setBounds(getX(), getY(), getWidth(), getHeight());

        setTitle(card.localCardString.name);

        if (card.localCardString.cardType == CardType.Virus) {
            setHealth(card.finalHealth);
        } else {
            setHealth(null);
        }
    }

    public void setTitle(String text) {
        if (Utils.isNullOrEmpty(text)) {
            titleText.setVisible(false);
        } else {
            titleText.setFontScale(cardViewState.cardSize.getScale());
            titleText.setBound(getX(), getY() + getHeight() * 0.02f, getWidth(), titleText.getFontSize(), Align.center, false);
            titleText.setText(text);
            titleText.setVisible(true);
        }
    }

    public void setHealth(Integer healthPoint) {
        this.health = healthPoint;
        if (health != null) {
            health = Math.max(0, health);
            healthText.setText(health.toString());
            healthText.setFontScale(cardViewState.cardSize.getNext().getScale());
            healthText.setBound(getX() + getWidth() * 0.1f, getY() + getHeight() * 0.9f - healthText.getFontSize(), getWidth(), healthText.getFontSize(), Align.left, false);
            healthText.setVisible(true);
        } else {
            healthText.setVisible(false);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        autoGroup.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (card == null) {
            return;
        }
        autoGroup.draw(batch, parentAlpha);
    }


    private float getHealthDecorateOffsetX() {
        return cardViewState.cardSize.getDecorateSize() * 0.2f;
    }

    private float getHealthDecorateOffsetY() {
        return getHeight();
    }
}
