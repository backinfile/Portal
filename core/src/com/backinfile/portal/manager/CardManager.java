package com.backinfile.portal.manager;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.skills.Skill;
import com.backinfile.support.Utils;

import java.util.HashMap;

public class CardManager {
    private static final HashMap<String, LocalString.LocalCardString> cardPool = new HashMap<>();

    public static void init() {
        for (LocalString.LocalCardString cardString : LocalString.getAllCardString()) {
            if (cardString.test) {
                continue;
            }
            cardPool.put(cardString.sn, cardString);
        }
    }

    public static Card buildCard(String sn) {
        return buildCard(LocalString.getCardString(sn));
    }

    public static Card buildCard(LocalString.LocalCardString localCardString) {
        Card card = new Card();
        card.id = Utils.applyId();
        card.localCardString = localCardString;

        if (localCardString.skills != null) {
            for (LocalString.DSkill dSkill : localCardString.skills) {
                Skill skill = SkillManager.build(dSkill.sn, dSkill.args);
                card.skillGroup.addSkill(skill);
            }
        }
        return card;
    }

}
