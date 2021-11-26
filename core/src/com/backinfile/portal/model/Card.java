package com.backinfile.portal.model;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.model.skills.GenDataSkill;
import com.backinfile.portal.model.skills.GenHashSkill;
import com.backinfile.portal.model.skills.SkillGroup;

public class Card {
    public long id;
    public LocalString.LocalCardString localCardString;

    public int data;
    public int hash;
    public final SkillGroup skillGroup = new SkillGroup();

    public Card() {
    }

    public void calcState() {
        {
            int finalHash = 0;
            for (GenHashSkill skill : skillGroup.getSkills(GenHashSkill.class)) {
                finalHash += skill.getNumber();
            }
            this.hash = finalHash;
        }
        {
            int finalData = 0;
            for (GenDataSkill skill : skillGroup.getSkills(GenDataSkill.class)) {
                finalData += skill.getNumber();
            }
            this.data = finalData;
        }
    }

}
