package com.backinfile.portal.view.screens;

import com.backinfile.portal.Log;
import com.backinfile.portal.manager.CardManager;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.portal.view.group.CardView;
import com.backinfile.support.Time2;
import com.backinfile.support.timer.TimerQueue;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage {

    private TimerQueue timerQueue = new TimerQueue();

    public GameStage() {

        final float cardWidth = CardSize.Normal.getWidth();
        final float cardHeight = CardSize.Normal.getHeight();

        for (int i = 0; i < 6; i++) {
            Card card = CardManager.buildCard("Test" + (i + 1));
            CardView cardView = new CardView();
            cardView.setCard(card);
            addActor(cardView);

            final int index = i;
            timerQueue.applyTimer(Time2.SEC, () -> {
                CardView.CardViewState cardViewState = new CardView.CardViewState();
                cardViewState.x = cardWidth / 2f + index * cardWidth + index * cardWidth * 0.1f;
                cardViewState.y = getHeight() - cardHeight / 2f - cardHeight * 0.2f;
                cardViewState.scale = CardSize.Normal.getScale();
                Log.game.info("{},{}", cardViewState.x, cardViewState.y);
                cardView.setTargetCardViewState(cardViewState);
            });
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timerQueue.update();
    }
}
