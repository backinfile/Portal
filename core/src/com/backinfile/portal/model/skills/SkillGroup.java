package com.backinfile.portal.model.skills;

import com.backinfile.support.StreamUtils;
import com.backinfile.support.func.Predicate;

import java.util.ArrayList;
import java.util.List;

public class SkillGroup {
    private final List<Skill> skillList = new ArrayList<>();

    public void addSkill(Skill skill) {
        skillList.add(skill);
    }

    public void removeSkill(Skill skill) {
        skillList.remove(skill);
    }

    public void removeSkills(Predicate<Skill> predicate) {
        StreamUtils.removeIf(skillList, predicate);
    }

    public void removeSkills(SkillDuration skillDuration) {
        removeSkills(skill -> skill.skillDuration == skillDuration);
    }

    public List<Skill> getSkills(Predicate<Skill> predicate) {
        return StreamUtils.filter(skillList, predicate);
    }

    @SuppressWarnings("unchecked")
    public <T extends Skill> List<T> getSkills(Class<T> clazz) {
        return (List<T>) StreamUtils.filter(skillList, clazz::isInstance);
    }

    public void clear() {
        skillList.clear();
    }

}
