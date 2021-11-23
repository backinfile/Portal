package com.backinfile.portal.view.actor;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Res;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.CardType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class CardView extends Actor {
    public static final LocalString.LocalUIString uiString = LocalString.getUIString("card");

    private Color borderColor = new Color(Color.RED);
    private Sprite borderImage;
    private Sprite mainImage;

    private Card card;
    private CardViewState cardViewState;

    private Integer health = null;


    private final AutoGroup autoGroup = new AutoGroup();
    private final AutoLabel titleText = new AutoLabel();
    private AutoLabel healthText = new AutoLabel();

    public CardView() {
        titleText.setVisible(false);
        healthText.setVisible(false);
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
        borderImage = Res.newSprite(uiString.images[0]);
        mainImage = Res.newSprite(card.localCardString.image);

        switch (card.localCardString.cardType) {
            case Virus: {
                borderColor.set(Color.RED);
                break;
            }
            case Command: {
                borderColor.set(Color.BLUE);
                break;
            }
            case Data: {
                borderColor.set(Color.GREEN);
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
        borderImage.setColor(borderColor);
        mainImage.setBounds(getX(), getY(), getWidth(), getHeight());

        titleText.setFontScale(cardViewState.cardSize.getScale());
        titleText.setBound(getX(), getY() + getHeight() * 0.02f, getWidth(), titleText.getFontSize(), Align.center, false);
        titleText.setText(card.localCardString.name);
        titleText.setVisible(true);

        if (card.localCardString.cardType == CardType.Virus) {
            setHealth(card.finalHealth);
        } else {
            setHealth(null);
        }
    }

    public void setHealth(Integer health) {
        this.health = health;
        if (health != null) {
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


        batch.setColor(getColor());
        mainImage.draw(batch);

        batch.setColor(borderColor);
        borderImage.draw(batch);


        titleText.draw(batch, parentAlpha);
        autoGroup.draw(batch, parentAlpha);
    }


    private float getHealthDecorateOffsetX() {
        return cardViewState.cardSize.getDecorateSize() * 0.2f;
    }

    private float getHealthDecorateOffsetY() {
        return getHeight();
    }
}
