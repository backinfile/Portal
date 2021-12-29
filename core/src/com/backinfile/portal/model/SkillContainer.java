package com.backinfile.portal.model;

import com.backinfile.support.func.Predicate;

import java.util.ArrayList;
import java.util.Iterator;

public class SkillContainer implements Iterable<Skill> {
    private final ArrayList<Skill> skills = new ArrayList<>();

    @Override
    public Iterator<Skill> iterator() {
        return skills.iterator();
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public boolean removeSkill(Skill skill) {
        return skills.remove(skill);
    }

    public void removeIf(Predicate<Skill> predicate) {
        skills.removeIf(predicate::test);
    }
}
