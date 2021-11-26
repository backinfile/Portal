package com.backinfile.portal.model.skills;


public class GenDataSkill extends Skill {
    private int number;

    public GenDataSkill() {
        this(1);
    }

    public GenDataSkill(int number) {
        this.number = number;
        setTrigger(SkillDuration.Fixed, SkillTrigger.None);
        setSkillTargetType(SkillTargetType.None);
    }

    public int getNumber() {
        return number;
    }

    public static class Gen2DataSkill extends GenDataSkill {
        public Gen2DataSkill() {
            super(2);
        }
    }

    public static class Gen3DataSkill extends GenDataSkill {
        public Gen3DataSkill() {
            super(3);
        }
    }

    public static class Gen4DataSkill extends GenDataSkill {
        public Gen4DataSkill() {
            super(4);
        }
    }
}

