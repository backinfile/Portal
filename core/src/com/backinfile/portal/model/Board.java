package com.backinfile.portal.model;

import com.backinfile.portal.manager.GameUtils;
import com.backinfile.portal.msg.GameMsgHandler;
import com.backinfile.support.ActionQueue;
import com.backinfile.support.IAlive;
import com.backinfile.support.Random;
import com.backinfile.support.StreamUtils;

import java.util.*;

public class Board implements IAlive {

    public int monsterCardSlotNumber = 2;
    public int numberCardSlotNumber = 5;
    public final CardPile monsterPile = new CardPile();
    public final CardPile monsterShop = new CardPile();
    public final CardPile monsterDiscardPile = new CardPile();
    public final CardPile numberPile = new CardPile();
    public final CardPile numberShop = new CardPile();
    public final CardPile numberDiscardPile = new CardPile();
    private final HashMap<GameMsgHandler.EPileType, CardPile> cardPiles = new HashMap<>();

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

        cardPiles.put(GameMsgHandler.EPileType.NumberPile, numberPile);
        cardPiles.put(GameMsgHandler.EPileType.NumberShop, numberShop);
        cardPiles.put(GameMsgHandler.EPileType.NumberDiscardPile, numberDiscardPile);
        cardPiles.put(GameMsgHandler.EPileType.MonsterShop, monsterShop);
        cardPiles.put(GameMsgHandler.EPileType.MonsterPile, monsterPile);
        cardPiles.put(GameMsgHandler.EPileType.MonsterDiscardPile, monsterDiscardPile);

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

        for (Human human : humanList) {
            sendBoardInitMsg(human.getToken());
        }
    }

    public void sendBoardInitMsg(String toHumanToken) {
        GameMsgHandler.SCBoardInit boardInit = new GameMsgHandler.SCBoardInit();
        boardInit.setHumans(getHumanData(toHumanToken));
        boardInit.setCards(getAllCardInfo(toHumanToken));
        sendMessage(toHumanToken, boardInit);
    }

    private GameMsgHandler.SCHumanUpdate getHumanData(String token) {
        GameMsgHandler.SCHumanUpdate humanUpdate = new GameMsgHandler.SCHumanUpdate();
        for (Human human : humanList) {
            GameMsgHandler.DHuman dHuman = new GameMsgHandler.DHuman();
            dHuman.setToken(human.getToken());
            dHuman.setActionPoint(human.actionPoint);
            dHuman.setWinPoint(human.winPoint);
            dHuman.setDiamond(human.diamond);
            dHuman.setHandPileSize(human.handPile.size());
            dHuman.setMonsterPileSize(human.fieldMonsterPile.size());
            humanUpdate.addHumans(human);
        }
        return humanUpdate;
    }

    private GameMsgHandler.SCCardsMove getAllCardInfo(String token) {
        GameMsgHandler.SCCardsMove cardsMove = new GameMsgHandler.SCCardsMove();
        cardsMove.addAllCards(getAllCardInfos());
        return cardsMove;
    }


    public CardPile getAllCards() {
        CardPile cardPile = new CardPile();
        StreamUtils.map(cardPiles.values(), cardPile::addAll);
        for (Human human : humanList) {
            cardPile.addAll(human.getAllCards());
        }
        return cardPile;
    }

    public boolean contains(Card card) {
        for (CardPile cardPile : cardPiles.values()) {
            if (cardPile.contains(card)) {
                return true;
            }
        }
        for (Human human : humanList) {
            if (human.contains(card)) {
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
        for (Human human : humanList) {
            if (human.removeCard(card)) {
                return true;
            }
        }
        return false;
    }

    public GameMsgHandler.DCard getCardInfo(Card card) {
        GameMsgHandler.DCard dCard = new GameMsgHandler.DCard();
        dCard.setId(card.id);
        dCard.setSn(card.localCardString.sn);
        GameMsgHandler.DCardPosition dCardPosition = new GameMsgHandler.DCardPosition();
        dCard.setPosition(dCardPosition);

        for (Human human : humanList) {
            for (Map.Entry<GameMsgHandler.EPileType, CardPile> entry : human.cardPiles.entrySet()) {
                CardPile cardPile = entry.getValue();
                if (cardPile.contains(card)) {
                    dCardPosition.setOwnerToken(human.getToken());
                    dCardPosition.setPileType(entry.getKey());
                    dCardPosition.setPileIndex(cardPile.indexOf(card));
                    dCardPosition.setPileSize(cardPile.size());
                    return dCard;
                }
            }
        }

        for (Map.Entry<GameMsgHandler.EPileType, CardPile> entry : cardPiles.entrySet()) {
            CardPile cardPile = entry.getValue();
            if (cardPile.contains(card)) {
                dCardPosition.setPileType(entry.getKey());
                dCardPosition.setPileIndex(cardPile.indexOf(card));
                dCardPosition.setPileSize(cardPile.size());
                return dCard;
            }
        }
        return null;
    }

    public List<GameMsgHandler.DCard> getAllCardInfos() {
        List<GameMsgHandler.DCard> cardInfos = new ArrayList<>();
        for (Card card : getAllCards()) {
            cardInfos.add(getCardInfo(card));
        }
        return cardInfos;
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

    public void sendMessage(String token, GameMsgHandler.DSyncBase msg) {
        if (GameUtils.AI_TOKEN.equals(token)) {
            return;
        }
        outputMsgMap.computeIfAbsent(token, key -> new LinkedList<>()).add(msg.toMessage());
    }

}
