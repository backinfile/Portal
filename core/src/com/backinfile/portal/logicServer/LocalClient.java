package com.backinfile.portal.logicServer;

import com.backinfile.portal.manager.CardManager;
import com.backinfile.portal.manager.GameManager;
import com.backinfile.portal.manager.GameUtils;
import com.backinfile.portal.model.Board;
import com.backinfile.portal.model.Human;
import com.backinfile.portal.net.GameMessage;
import com.backinfile.support.IAlive;
import com.backinfile.support.Terminal;

import java.util.LinkedList;

public class LocalClient extends Terminal<GameMessage, GameMessage> implements IAlive {
    private Board board;

    @Override
    public void start() {
        board = new Board();
        board.humanList.add(new Human(GameManager.getToken()));
        board.humanList.add(new Human(GameUtils.AI_TOKEN));
        board.starter = board.humanList.get(0);

        board.monsterShop.addAll(CardManager.createMonsterShopPile());
        board.numberShop.addAll(CardManager.createNumberShopPile());
        board.init();
    }

    @Override
    public void update(long timeDelta) {
        // board 处理来自玩家的消息
        String token = GameManager.getToken();
        while (hasMsg()) {
            GameMessage gameMessage = pollMsg();
            board.onMessage(token, gameMessage.getMessage());
        }

        // board 推送消息
        if (board.outputMsgMap.containsKey(token)) {
            LinkedList<String> msgList = board.outputMsgMap.get(token);
            while (!msgList.isEmpty()) {
                String msg = msgList.pollFirst();
                outputMsg(new GameMessage(msg));
            }
        }

        // 更新游戏逻辑
        board.update(timeDelta);
    }

    @Override
    public void dispose() {
        if (board != null) {
            board.dispose();
        }
    }
}
