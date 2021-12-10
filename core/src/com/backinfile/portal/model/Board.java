package com.backinfile.portal.model;

import com.backinfile.portal.msg.GameMsgHandler;
import com.backinfile.support.ActionQueue;
import com.backinfile.support.IAlive;
import com.backinfile.support.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Board implements IAlive {

    public int monsterCardSlotNumber = 2;
    public int numberCardSlotNumber = 5;
    public final CardPile monsterPile = new CardPile();
    public final CardPile monsterShop = new CardPile();
    public final CardPile numberPile = new CardPile();
    public final CardPile numberShop = new CardPile();

    public boolean curTurnOver = false;
    public int turnCount = 0;
    public int playerTurnCount = 0;
    public Human curTurnHuman;
    public Random random = new Random();

    public final List<Human> humanList = new ArrayList<>();
    public Human starter;

    public final HashMap<String, LinkedList<String>> outputMsgMap = new HashMap<>();
    private ActionQueue<GameAction> actionQueue;
    private BoardState state = BoardState.None;

    public void init() {
        actionQueue = new ActionQueue<>(action -> {
            action.board = this;
        });

        monsterShop.shuffle(random);
        numberShop.shuffle(random);
        for (int i = 0; i < monsterCardSlotNumber; i++) {
            monsterPile.add(monsterShop.pollTop());
        }
        for (int i = 0; i < numberCardSlotNumber; i++) {
            numberPile.add(numberShop.pollTop());
        }
    }

    @Override
    public void start() {
        state = BoardState.GamePrepare;
    }

    @Override
    public void update(long timeDelta) {
        // 游戏还没开始
        if (state == BoardState.None) {
            return;
        }

        for (Human human : humanList) {
            human.updateHumanOper(timeDelta);
        }
        if (humanList.stream().anyMatch(Human::hasHumanOper)) {
            return;
        }

        checkBoardState();

        actionQueue.update(timeDelta);
    }

    private void checkBoardState() {
        boolean changeState = false;
        if (actionQueue.isEmpty() && humanList.stream().noneMatch(Human::hasHumanOper)) {
            if (state != BoardState.InTurn || curTurnOver) {
                changeState = true;
            }
        }

        if (!changeState) {
            return;
        }

        state = state.next();
        switch (state) {
            case None:
                break;
            case GamePrepare:
                turnCount = 0;
                playerTurnCount = 0;
                for (Human human : humanList) {
                    human.onGamePrepare();
                }
                break;
            case TurnStart:
                curTurnOver = false;
                // 增加轮次计数
                if (playerTurnCount % humanList.size() == 0) {
                    turnCount++;
                }
                // 回合开始，找到当前回合玩家
                if (curTurnHuman == null) {
                    curTurnHuman = starter;
                } else {
                    int index = humanList.indexOf(curTurnHuman) + 1;
                    curTurnHuman = humanList.get(index % humanList.size());
                }
                // 回合开始
                playerTurnCount++;
                curTurnHuman.onTurnStart();
                break;
            case InTurn:
                curTurnHuman.onEnterTurn();
                break;
            case TurnEnd:
                curTurnHuman.onTurnEnd();
                break;
        }
    }

    @Override
    public void dispose() {
        actionQueue.clear();
    }

    public ActionQueue<GameAction> getActionQueue() {
        return actionQueue;
    }

    public void onMessage(String token, String content) {
        for (Human human : humanList) {
            if (human.getToken().equals(token)) {
                human.onMessage(content);
                break;
            }
        }
    }

    public void sendMessage(Human human, GameMsgHandler.DSyncBase msg) {
        if (human.isAI()) {
            return;
        }
        outputMsgMap.computeIfAbsent(human.getToken(), key -> new LinkedList<>()).add(msg.toMessage());
    }

}
