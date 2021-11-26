package com.backinfile.portal.model.skills;

public class GenHashSkill extends Skill {
    private int number;

    public GenHashSkill() {
        this(1);
    }

    public GenHashSkill(int number) {
        this.number = number;
        setTrigger(SkillDuration.Fixed, SkillTrigger.None);
        setSkillTargetType(SkillTargetType.None);
    }

    public int getNumber() {
        return number;
    }

    public static class Gen2HashSkill extends GenHashSkill {
        public Gen2HashSkill() {
            super(2);
        }
    }

    public static class Gen3HashSkill extends GenHashSkill {
        public Gen3HashSkill() {
            super(3);
        }
    }

    public static class Gen4HashSkill extends GenHashSkill {
        public Gen4HashSkill() {
            super(4);
        }
    }
}
