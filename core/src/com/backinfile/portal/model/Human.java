package com.backinfile.portal.model;

import com.backinfile.portal.Log;
import com.backinfile.portal.manager.GameUtils;
import com.backinfile.support.AutoSet;

public class Human extends HumanOperContainer {
    @AutoSet
    public Board board;

    private final String token;


    public int actionPoint = 0;
    public int winPoint = 0;
    public int diamond = 0;

    public CardPile handPile = new CardPile();
    public CardPile slotPile = new CardPile();
    public CardPile monsterPile = new CardPile();

    public Human(String token) {
        this.token = token;
    }

    public void init() {

    }

    public void onGamePrepare() {
    }

    public void onTurnStart() {
    }

    public void onEnterTurn() {
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
