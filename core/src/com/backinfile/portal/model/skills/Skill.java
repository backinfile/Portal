package com.backinfile.portal.model.skills;

import com.backinfile.portal.model.Board;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.Human;
import com.backinfile.support.AutoSet;

public abstract class Skill {
    public long id;
    @AutoSet
    public Board board;
    @AutoSet
    public Human human;
    @AutoSet
    public Card targetCard = null;

    // 生效时间
    public SkillDuration skillDuration = SkillDuration.AuraCheck;

    // 触发时机
    public SkillTrigger skillTrigger = SkillTrigger.None;
    public int triggerLimit = -1;
    public int triggerLimitMax = -1;

    // 是否需要目标
    public SkillTargetType skillTargetType;

    public Skill() {
    }

    public void setTrigger(SkillDuration skillDuration, SkillTrigger skillTrigger) {
        setTrigger(skillDuration, skillTrigger, -1);
    }

    public void setTrigger(SkillDuration skillDuration, SkillTrigger skillTrigger, int limit) {
        this.skillDuration = skillDuration;
        this.skillTrigger = skillTrigger;
        this.triggerLimitMax = this.triggerLimit = limit;
    }

    public void setSkillTargetType(SkillTargetType skillTargetType) {
        this.skillTargetType = skillTargetType;
    }


    public boolean triggerable() {
        return true;
    }

    public void apply() {

    }
}
