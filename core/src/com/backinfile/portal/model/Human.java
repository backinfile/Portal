package com.backinfile.portal.model;

import com.backinfile.portal.Log;
import com.backinfile.portal.manager.GameUtils;
import com.backinfile.portal.msg.GameMsgHandler;
import com.backinfile.support.AutoSet;
import com.backinfile.support.StreamUtils;

import java.util.HashMap;

public class Human extends HumanOperContainer {
    @AutoSet
    public Board board;

    private final String token;


    public int actionPoint = 0;
    public int winPoint = 0;
    public int diamond = 0;
    public int actionPointResetValue = 3;

    public CardPile handPile = new CardPile();
    public CardPile gatePile = new CardPile();
    public CardPile fieldMonsterPile = new CardPile();
    public final HashMap<GameMsgHandler.EPileType, CardPile> cardPiles = new HashMap<>();

    public Human(String token) {
        this.token = token;
    }

    public void init() {
        cardPiles.put(GameMsgHandler.EPileType.Hand, handPile);
        cardPiles.put(GameMsgHandler.EPileType.Gate, gatePile);
        cardPiles.put(GameMsgHandler.EPileType.FieldMonster, fieldMonsterPile);
    }

    public CardPile getAllCards() {
        CardPile cardPile = new CardPile();
        StreamUtils.map(cardPiles.values(), cardPile::addAll);
        return cardPile;
    }

    public boolean contains(Card card) {
        for (CardPile cardPile : cardPiles.values()) {
            if (cardPile.contains(card)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeCard(Card card) {
        for (CardPile cardPile : cardPiles.values()) {
            if (cardPile.remove(card)) {
                return true;
            }
        }
        return false;
    }

    public void onGamePrepare() {
    }

    public void onTurnStart() {
    }

    public void onEnterTurn() {
        this.actionPoint = actionPointResetValue;
    }

    public void onTurnEnd() {
    }

    public boolean isAI() {
        return token.equals(GameUtils.AI_TOKEN);
    }

    public String getToken() {
        return token;
    }

    @Override
    public void addHumanOper(HumanOper humanOper) {
        humanOper.human = this;
        Log.game.info("attach humanOper {} for {}", humanOper.getClass().getSimpleName(), getToken());
        super.addHumanOper(humanOper);
    }

    @Override
    public void removeHumanOper(HumanOper humanOper) {
        Log.game.info("{} onDetach for {}", humanOper.getClass().getSimpleName(), getToken());
        super.removeHumanOper(humanOper);

    }

}
