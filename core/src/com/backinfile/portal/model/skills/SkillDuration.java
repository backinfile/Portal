package com.backinfile.portal.model.skills;

public class SkillDuration {
    /**
     * 固有技能
     */
    public static final SkillDuration Fixed = new SkillDuration(1 << 1);
    /**
     * 表示仅在本次游戏中生效
     */
    public static final SkillDuration Combat = new SkillDuration(1 << 2);
    /**
     * 仅能触发一次
     */
    public static final SkillDuration AfterTrigger = new SkillDuration(1 << 3);
    /**
     * 每次光环检查时更新
     */
    public static final SkillDuration AuraCheck = new SkillDuration(1 << 4);
    public static final SkillDuration TurnEnd = new SkillDuration(1 << 5);
    public static final SkillDuration TurnStart = new SkillDuration(1 << 5);

    private final int value;

    private SkillDuration(int value) {
        this.value = value;
    }

    public static SkillDuration combine(SkillDuration... skillDurations) {
        int result = 0;
        for (SkillDuration skillDuration : skillDurations) {
            result |= skillDuration.value;
        }
        return new SkillDuration(result);
    }

    public boolean test(SkillDuration skillDuration) {
        return (value & skillDuration.value) > 0;
    }
}
