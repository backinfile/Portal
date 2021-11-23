package com.backinfile.portal.view.screens;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.portal.view.group.CardView;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage {
    public GameStage() {
        Card card = new Card(LocalString.getCardString("virus"));

        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth();
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }

        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth() * 2 + CardSize.Large.getWidth();
            cardViewState.cardSize = CardSize.Large;
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }
        {
            CardView cardView = new CardView();
            cardView.setCard(card);
            CardView.CardViewState cardViewState = new CardView.CardViewState();
            cardViewState.x = CardSize.Normal.getWidth() * 2 + CardSize.Large.getWidth() * 2 + CardSize.LargeLarge.getWidth();
            cardViewState.cardSize = CardSize.LargeLarge;
            cardView.setCardViewState(cardViewState);
            addActor(cardView);
        }
    }
}
