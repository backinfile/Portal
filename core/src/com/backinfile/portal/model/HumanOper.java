package com.backinfile.portal.model;

import com.backinfile.portal.manager.ConstGame;
import com.backinfile.portal.manager.GameUtils;
import com.backinfile.portal.msg.GameMsgHandler;
import com.backinfile.support.AutoSet;
import com.backinfile.support.func.Action0;

public abstract class HumanOper implements GameMsgHandler.DSyncListener {
    @AutoSet
    public Board board;
    @AutoSet
    public Human human;

    private Action0 callback;
    private boolean isDone = false;
    private long aiTimer = 0;

    public void onAttach() {
        if (GameUtils.isAI(human)) {
            aiTimer = ConstGame.AI_WAIT_TIME;
        } else {
            onHumanAttach();
        }
    }

    /**
     * 刚被附加到Human上时执行
     */
    public abstract void onHumanAttach();

    /**
     * 刚刚被附加到AI上时执行
     */
    public abstract void onAIAttach();

    public void update(long timeDelta) {
        if (aiTimer > 0) {
            aiTimer -= timeDelta;
            if (aiTimer <= 0) {
                onAIAttach();
            }
        }
    }

    /**
     * 从Human上移除时执行
     */
    public void onDetach() {
        if (callback != null) {
            callback.invoke();
        }
    }

    public void setDone() {
        this.isDone = true;
    }

    /**
     * 是否已经操作结束
     */
    public boolean isDone() {
        return isDone;
    }

    public final void setDetachCallback(Action0 callback) {
        this.callback = callback;
    }

}
