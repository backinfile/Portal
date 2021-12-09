package com.backinfile.portal.model.skills;

import com.backinfile.portal.model.Skill;
import com.backinfile.portal.model.SkillDuration;
import com.backinfile.portal.model.SkillTriggerType;

public class ProvideNumberCardSkill extends Skill {
    private final int number;

    public ProvideNumberCardSkill(int number) {
        this.number = number;
        setTrigger(SkillDuration.Fixed, SkillTriggerType.AtCost);
    }

    public int getNumber() {
        return number;
    }
}
