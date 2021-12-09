package com.backinfile.portal.model.skills;

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
