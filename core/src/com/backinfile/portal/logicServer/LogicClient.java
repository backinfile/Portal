package com.backinfile.portal.logicServer;

import com.backinfile.gameRPC.net.GameMessage;
import com.backinfile.support.IAlive;
import com.backinfile.support.Terminal;

public class LogicClient extends Terminal<GameMessage, GameMessage> implements IAlive {
    public String viewHumanToken;
    public boolean watch = false;

    @Override
    public void start() {
    }

    @Override
    public void update(long timeDelta) {
        // 处理消息
        while (hasMsg()) {
            GameMessage msg = pollMsg();
//            GameMsgHandler.DSyncBase struct = GameMsgHandler.parseStruct(msg.getMessage());
        }
    }

    @Override
    public void dispose() {

    }
}
