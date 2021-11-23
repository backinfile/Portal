package com.backinfile.portal.view.screens;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.portal.view.group.CardView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage {
    public GameStage() {
        Card card = new Card(LocalString.getCardString("virus"));

        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth();
            cardViewState.y = Gdx.graphics.getHeight() / 2f;
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }

        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth() + CardSize.Large.getWidth();
            cardViewState.y = Gdx.graphics.getHeight() / 2f;
            cardViewState.cardSize = CardSize.Large;
            cardViewState.scale = 0.5f;
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }
        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth() + CardSize.Large.getWidth() * 2;
            cardViewState.y = Gdx.graphics.getHeight() / 2f;
            cardViewState.cardSize = CardSize.Large;
            cardViewState.color = Color.GRAY;
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }
        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth() * 2 + CardSize.Large.getWidth() * 2 + CardSize.LargeLarge.getWidth();
            cardViewState.y = Gdx.graphics.getHeight() / 2f;
            cardViewState.cardSize = CardSize.LargeLarge;
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }
    }
}
