package com.backinfile.portal.manager;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.CardPile;
import com.backinfile.portal.model.CardType;
import com.backinfile.portal.model.Skill;
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

    public static CardPile createMonsterShopPile() {
        CardPile cardPile = new CardPile();
        for (LocalString.LocalCardString localCardString : cardPool.values()) {
            if (localCardString.cardType == CardType.Monster) {
                int copy = localCardString.copy;
                for (int i = 0; i < copy; i++) {
                    cardPile.add(buildCard(localCardString));
                }
            }
        }
        return cardPile;
    }

    public static CardPile createNumberShopPile() {
        CardPile cardPile = new CardPile();
        for (LocalString.LocalCardString localCardString : cardPool.values()) {
            if (localCardString.cardType == CardType.Number) {
                int copy = localCardString.copy;
                for (int i = 0; i < copy; i++) {
                    cardPile.add(buildCard(localCardString));
                }
            }
        }
        return cardPile;
    }
}
