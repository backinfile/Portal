package com.backinfile.portal.model;

import com.backinfile.support.ActionQueue;
import com.backinfile.support.IAlive;

import java.util.ArrayList;
import java.util.List;

public class Board implements IAlive {

    public final CardPile monsterPile = new CardPile();
    public final CardPile monsterShop = new CardPile();
    public final CardPile numberPile = new CardPile();
    public final CardPile numberShop = new CardPile();

    public int monsterCardSlotNumber = 2;
    public int numberCardSlotNumber = 5;

    public final List<Human> humanList = new ArrayList<>();

    private ActionQueue<GameAction> actionQueue;

    public static enum BoardState {
        None, // 未开始
        GamePrepare, // 进入准备阶段
        TurnStart, // 进入回合开始阶段
        InTurn, // 回合进行中
        TurnEnd, // 回合结束阶段
    }

    public void init() {
        actionQueue = new ActionQueue<>(action -> {
            action.board = this;
        });
    }

    @Override
    public void start() {
    }

    @Override
    public void update(long timeDelta) {
        actionQueue.update(timeDelta);
    }

    @Override
    public void dispose() {
        actionQueue.clear();

    }

    public ActionQueue<GameAction> getActionQueue() {
        return actionQueue;
    }

}
