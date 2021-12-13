package com.backinfile.portal.model.humanOpers;

import com.backinfile.portal.model.CardPile;
import com.backinfile.portal.model.HumanOper;
import com.backinfile.portal.msg.GameMsgHandler;

import java.util.List;

public class SelectCardsOper extends HumanOper {

    private final CardPile selectFrom = new CardPile();
    private final int minNumber;
    private final int maxNumber;
    private final String tip;

    private final CardPile selected = new CardPile();

    public SelectCardsOper(CardPile selectFrom, int minNumber, int maxNumber, String tip) {
        this.selectFrom.addAll(selectFrom);
        this.tip = tip;

        this.maxNumber = maxNumber < 0 ? selectFrom.size() : maxNumber;
        this.minNumber = Math.min(Math.max(minNumber, 0), selectFrom.size());
    }

    @Override
    public void onHumanAttach() {
        GameMsgHandler.SCSelectCards selectCards = new GameMsgHandler.SCSelectCards();
        selectCards.addAllIdList(selectFrom.getCardIdList());
        selectCards.setMinNumber(minNumber);
        selectCards.setMaxNumber(maxNumber);
        selectCards.setTip(tip);
        human.board.sendMessage(human.getToken(), selectCards);
    }

    @Override
    public void onAIAttach() {
        selected.addAll(selectFrom.pollRandom(human.board.random, maxNumber));
        setDone();
    }

    @Override
    public boolean onMessage(GameMsgHandler handler, GameMsgHandler.CSSelectCards msg) {
        List<Long> idList = msg.getIdListList();
        selected.addAll(selectFrom.filter(c -> idList.contains(c.id)));
        setDone();
        return true;
    }

    public CardPile getSelected() {
        return selected;
    }
}
