package com.backinfile.portal.manager;

import com.backinfile.portal.LocalString;
import com.backinfile.portal.Log;
import com.backinfile.portal.model.Card;
import com.backinfile.portal.model.CardType;
import com.backinfile.support.Random;
import com.backinfile.support.StreamUtils;
import com.backinfile.support.Utils;

import java.util.HashMap;
import java.util.List;

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

    public Card randomCard(Random random, int star, CardType cardType) {
        List<LocalString.LocalCardString> virus = StreamUtils.filter(cardPool.values(), str -> str.star == star && str.cardType == cardType);
        if (virus.isEmpty()) {
            Log.res.error("no {} card in star:{}", cardType.name(), star);
            return null;
        }
        return buildCard(random.randItem(virus));
    }

    public Card randomMainCard(Random random, int star) {
        List<LocalString.LocalCardString> virus = StreamUtils.filter(cardPool.values(), str -> str.star == star && str.cardType.isMainType());
        if (virus.isEmpty()) {
            Log.res.error("no main card in star:{}", star);
            return null;
        }
        return buildCard(random.randItem(virus));
    }


    public static Card buildCard(String sn) {
        return buildCard(LocalString.getCardString(sn));
    }

    public static Card buildCard(LocalString.LocalCardString localCardString) {
        Card card = new Card();
        card.id = Utils.applyId();
        card.localCardString = localCardString;
        card.calcState();
        return card;
    }

}
