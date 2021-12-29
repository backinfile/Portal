package com.backinfile.portal.view.group;

import com.backinfile.portal.logicServer.LocalClient;
import com.backinfile.portal.msg.GameMsgHandler;
import com.backinfile.support.Time2;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.LinkedList;

public class BoardView extends Group {
    private LocalClient localClient;
    private final LinkedList<GameMsgHandler.DSyncBase> viewActionQueue = new LinkedList<>();

    public BoardView() {

    }


    public void startGame() {
        viewActionQueue.clear();

        localClient = new LocalClient();
        localClient.addOutputListener(msg -> {
//            String content = msg.getMessage();
//            viewActionQueue.add(GameMsgHandler.parseStruct(content));
        });
        localClient.start();
    }

    public void stopGame() {
        localClient.dispose();
        localClient = null;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        long timeDelta = Time2.getTimestamp(delta);
        if (localClient != null) {
            localClient.update(timeDelta);
        }
    }

}
