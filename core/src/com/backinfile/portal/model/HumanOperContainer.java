package com.backinfile.portal.model;

import com.backinfile.portal.msg.GameMsgHandler;

import java.util.ArrayList;
import java.util.List;

public class HumanOperContainer {
    private final GameMsgHandler gameMsgHandler = new GameMsgHandler();
    private final List<HumanOper> humanOperList = new ArrayList<>();

    public boolean hasHumanOper() {
        return !humanOperList.isEmpty();
    }

    public void addHumanOper(HumanOper humanOper) {
        gameMsgHandler.addListener(humanOper);
        humanOperList.add(humanOper);
        humanOper.onAttach();
    }

    public void removeHumanOper(HumanOper humanOper) {
        gameMsgHandler.removeListener(humanOper);
        humanOperList.remove(humanOper);
        humanOper.onDetach();
    }

    public void updateHumanOper(long delta) {
        List<HumanOper> doneOperList = new ArrayList<>();
        for (HumanOper humanOper : humanOperList) {
            if (humanOper.isDone()) {
                doneOperList.add(humanOper);
            } else {
                humanOper.update(delta);
                if (humanOper.isDone()) {
                    doneOperList.add(humanOper);
                }
            }
        }
        for (HumanOper humanOper : doneOperList) {
            removeHumanOper(humanOper);
        }
    }

    public boolean onMessage(String content) {
        return gameMsgHandler.onMessage(content, true);
    }
}
