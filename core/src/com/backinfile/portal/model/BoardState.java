package com.backinfile.portal.model;

public enum BoardState {
    None, // 未开始
    GamePrepare, // 进入准备阶段
    TurnStart, // 进入回合开始阶段
    InTurn, // 回合进行中
    TurnEnd, // 回合结束阶段
    ;

    public BoardState next() {
        switch (this) {
            case None:
                return GamePrepare;
            case GamePrepare:
            case TurnEnd:
                return TurnStart;
            case TurnStart:
                return InTurn;
            case InTurn:
                return TurnEnd;
            default:
                return None;
        }
    }
}
