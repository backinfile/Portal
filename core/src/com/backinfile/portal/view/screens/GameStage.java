package com.backinfile.portal.view.screens;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Log;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.group.CardView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameStage extends Stage {
    public GameStage() {
        Card card = new Card();
        card.localCardString = LocalString.getCardString("virus");

        CardView cardView = new CardView();
        cardView.setCard(card);
        cardView.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        cardView.scaleBy(2f);

        cardView.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Log.game.info("clicked");
            }
        });

        addActor(cardView);
    }
}
