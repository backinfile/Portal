package com.backinfile.portal.manager;

import com.backinfile.portal.model.Human;

public class GameUtils {

    public static final String AI_TOKEN = "AI";

    public static boolean isAI(Human human) {
        return human.isAI();
    }
}
