package com.backinfile.portal.model.skills;

public abstract class BodySkill extends Skill {
    public int health;

    public BodySkill(SkillDuration skillDuration, int health) {
        this.health = health;
        setTrigger(skillDuration, SkillTrigger.None);
        setSkillTargetType(SkillTargetType.None);
    }
}
