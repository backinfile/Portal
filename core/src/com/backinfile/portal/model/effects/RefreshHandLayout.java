package com.backinfile.portal.model.effects;

import com.backinfile.portal.ScreenSize;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.view.actor.CardSize;
import com.backinfile.portal.view.effects.Effect;
import com.backinfile.portal.view.group.CardView;

public class RefreshHandLayout extends Effect {
    @Override
    public void aniStart() {
        int size = human.handPile.size();
        float centerX = ScreenSize.STAGE_WIDTH / 2f;
        float centerY = CardSize.Normal.getHeight();
        float cardWidth = CardSize.Normal.getWidth();
        float gap = 0f;
        float centerIndex = size / 2f;

        for (int index = 0; index < size; index++) {
            Card card = human.handPile.get(index);
            CardView cardView = boardView.getCardView(card);
            CardView.CardViewState state = new CardView.CardViewState();
            state.x = (cardWidth + gap) * (index + 0.5f - centerIndex) + centerX;
            state.y = centerY;
            state.scale = CardSize.Normal.getScale();
            cardView.setTargetCardViewState(state);
        }
    }

    @Override
    public void aniUpdate() {

    }

    @Override
    public void aniEnd() {

    }
}
