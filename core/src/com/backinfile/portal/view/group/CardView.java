package com.backinfile.portal.view.group;

import com.backinfile.gdxSupport.GdxUtils;
import com.backinfile.portal.LocalString;
import com.backinfile.portal.Res;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.support.Param;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class CardView extends Group {
    public static final LocalString.LocalUIString uiString = LocalString.getUIString("card");
    public static final LocalString.LocalUIString cardTypeString = LocalString.getUIString("cardType");
    public static final LocalString.LocalUIString resourceString = LocalString.getUIString("resource");


    private Card card = null;
    private CardViewState cardViewState;
    private CardViewState targetCardViewState = null;

    //    private final Label titleLabel = new Label("", new Label.LabelStyle(Res.DefaultFont, Color.BLACK));
    private final Image mainImage = new Image();
    private final Image borderImage = new Image();

    private final Param actorParam = new Param();

    public CardView() {
        addActor(mainImage);
        addActor(borderImage);

        setSize(CardSize.Large.getWidth(), CardSize.Large.getHeight());
        setOrigin(Align.center);
        borderImage.setBounds(0, 0, getWidth(), getHeight());
        mainImage.setBounds(0, 0, getWidth(), getHeight());
    }

    public static class CardViewState {
        public static final CardViewState Normal = new CardViewState();

        public float x = 0;
        public float y = 0;
        public float scale = 1f;
        public Color color = Color.WHITE.cpy();

        public CardViewState copy() {
            CardViewState cardViewState = new CardViewState();
            cardViewState.x = x;
            cardViewState.y = y;
            cardViewState.scale = scale;
            cardViewState.color = color.cpy();
            return cardViewState;
        }
    }

    public void setCard(Card card) {
        // clear element
        actorParam.map((key, v) -> v.remove(), Actor.class);
        actorParam.clear();

        this.card = card;
        if (card == null) {
            borderImage.setDrawable(Res.getTexture(uiString.images[0]));
            mainImage.setDrawable(Res.getTexture(uiString.images[1]));
        } else {
            mainImage.setDrawable(Res.getTexture(card.localCardString.image));
            borderImage.setDrawable(Res.getTexture(cardTypeString.images[card.localCardString.cardType.ordinal()]));
            if (card.isMonsterCard()) {
                {
                    String cardName = card.localCardString.name;
                    Label titleLabel = new Label(cardName, new Label.LabelStyle(Res.DefaultFont, Color.BLACK));
                    titleLabel.setFontScale(CardSize.Large.getScale());
                    titleLabel.setAlignment(Align.center);
                    titleLabel.setWrap(false);
                    titleLabel.setBounds(0, getHeight() * 0.03f, getWidth(), CardSize.Large.getFontSize());
                    titleLabel.setText(cardName);
                    addActor(titleLabel);
                    actorParam.put("titleLabel", titleLabel);
                }
            } else {
                int number = card.localCardString.number;
                String numberValue = String.valueOf(number);
                {
                    Label numberTitleLabel = new Label(numberValue, new Label.LabelStyle(Res.DefaultFont, Color.BLACK));
                    numberTitleLabel.setFontScale(CardSize.Large.getScale());
                    numberTitleLabel.setAlignment(Align.left);
                    numberTitleLabel.setWrap(false);
                    numberTitleLabel.setBounds(getWidth() * 0.08f, getHeight() * 0.97f - CardSize.Large.getFontSize(), getWidth(), CardSize.Large.getFontSize());
                    numberTitleLabel.setText(numberValue);
                    addActor(numberTitleLabel);
                    actorParam.put("numberTitleLabel", numberTitleLabel);
                }

                {
                    Label numberCenterLabel = new Label(numberValue, new Label.LabelStyle(Res.DefaultFont, Color.BLACK));
                    float scale = 2.2f;
                    float fontSize = CardSize.Large.getFontSize() * scale;
                    float fontScale = CardSize.Large.getScale() * scale;
                    numberCenterLabel.setFontScale(fontScale);
                    numberCenterLabel.setAlignment(Align.center);
                    numberCenterLabel.setWrap(false);
                    numberCenterLabel.setBounds(0, 0, getWidth(), getHeight());
                    numberCenterLabel.setText(numberValue);
                    addActor(numberCenterLabel);
                    actorParam.put("numberCenterLabel", numberCenterLabel);
                }
            }
        }

        setCardViewState(CardViewState.Normal);

    }

    public void setCardViewState(CardViewState cardViewState) {
        this.cardViewState = cardViewState.copy();
        setScale(cardViewState.scale);
        setPosition(cardViewState.x, cardViewState.y, Align.center);
        setColor(cardViewState.color);
    }

    public void setTargetCardViewState(CardViewState targetCardViewState) {
        this.targetCardViewState = targetCardViewState;
    }

    public CardViewState getCardViewState() {
        return cardViewState;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        mainImage.setColor(cardViewState.color);
    }


//
//    public void updateCost() {
//        int cost = card.data;
//        int health = card.hash;
//
//        if (cost <= 0) {
//            costLabel.setVisible(false);
//            costIcon.setVisible(false);
//        } else {
//            CardSize cardSize = CardSize.Large;
//            float scale = 1.6f;
//            float fontScale = cardSize.getScale() * scale;
//            float fontSize = cardSize.getFontSize() * scale;
//            float gap = fontSize / 10f;
//
//            float heightOffset = health > 0 ? fontSize + gap * 2 : 0;
//
//            costLabel.setFontScale(cost < 10 ? fontScale : fontScale * 0.8f);
//            costLabel.setAlignment(Align.center);
//            costLabel.setWrap(false);
//            costLabel.setBounds(getWidth() * 0.95f - fontSize, getHeight() * 0.95f - fontSize - heightOffset, fontSize, fontSize);
//            costLabel.setText(cost);
//            costLabel.setVisible(true);
//
//
//            costIcon.setBounds(costLabel.getX() - gap, costLabel.getY() - gap,
//                    costLabel.getWidth() + 2 * gap, costLabel.getHeight() + 2 * gap);
//            costIcon.setColor(new Color(1, 1, 1, 0.6f));
//            costIcon.setVisible(true);
//        }
//    }

    private float getHealthDecorateOffsetX() {
        return CardSize.Large.getDecorateSize() * 0.2f;
    }

    private float getHealthDecorateOffsetY() {
        return getHeight();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (targetCardViewState == null) {
            return;
        }

        boolean stateChanged = false;

        if (!GdxUtils.isSame(targetCardViewState.scale, cardViewState.scale)) {
            stateChanged = true;
            float targetScale = GdxUtils.lerpStep(cardViewState.scale, targetCardViewState.scale);

            cardViewState.scale = targetScale;
            setScale(targetScale);
        }

        if (!GdxUtils.isSame(targetCardViewState.x, cardViewState.x) ||
                !GdxUtils.isSame(targetCardViewState.y, cardViewState.y)) {
            stateChanged = true;
            float targetX = GdxUtils.lerpStep(cardViewState.x, targetCardViewState.x);
            float targetY = GdxUtils.lerpStep(cardViewState.y, targetCardViewState.y);
//            Log.game.info("from {},{} to {},{} cur {},{}", cardViewState.x, cardViewState.y,
//                    targetCardViewState.x, targetCardViewState.y, targetX, targetY);
            cardViewState.x = targetX;
            cardViewState.y = targetY;
            setPosition(targetX, targetY, Align.center);
        }

        if (!targetCardViewState.color.equals(cardViewState.color)) {
            stateChanged = true;
            Color targetColor = new Color();
            targetColor.r = GdxUtils.lerpStep(cardViewState.color.r, targetCardViewState.color.r);
            targetColor.g = GdxUtils.lerpStep(cardViewState.color.g, targetCardViewState.color.g);
            targetColor.b = GdxUtils.lerpStep(cardViewState.color.b, targetCardViewState.color.b);
            targetColor.a = GdxUtils.lerpStep(cardViewState.color.a, targetCardViewState.color.a);
            cardViewState.color = targetColor;
            setColor(targetColor);
        }

        if (!stateChanged) {
            targetCardViewState = null;
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
