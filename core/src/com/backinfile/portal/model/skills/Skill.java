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
    public SkillDuration skillDuration = SkillDuration.Fixed;

    // 触发时机
    public SkillTriggerType skillTrigger = SkillTriggerType.None;
    public int triggerLimit = -1;
    public int triggerLimitMax = -1;

    public Skill() {
    }

    public void setTrigger(SkillDuration skillDuration, SkillTriggerType skillTrigger) {
        setTrigger(skillDuration, skillTrigger, -1);
    }

    public void setTrigger(SkillDuration skillDuration, SkillTriggerType skillTrigger, int limit) {
        this.skillDuration = skillDuration;
        this.skillTrigger = skillTrigger;
        this.triggerLimitMax = this.triggerLimit = limit;
    }

    public boolean triggerable() {
        return true;
    }

    public void apply() {

    }
}
