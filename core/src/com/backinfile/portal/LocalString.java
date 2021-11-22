package com.backinfile.portal;

import com.alibaba.fastjson.JSONObject;
import com.backinfile.portal.model.CardType;
import com.backinfile.support.Utils;
import com.backinfile.support.reflection.Timing;

import java.util.*;

public class LocalString {
    private static final String LocalCardStringKey = "cards";
    private static final String LocalSkillStringKey = "skills";
    private static final String LocalUIStringKey = "ui";

    private static Map<String, LocalCardString> cardStringMap = new HashMap<>();
    private static Map<String, LocalSkillString> skillStringMap = new HashMap<>();
    private static Map<String, LocalUIString> uiStringMap = new HashMap<>();
    private static List<LocalImagePathString> imagePathStringList = new ArrayList<>();

    @Timing("init local string")
    public static void init(String localStringConf) {
        JSONObject localString = JSONObject.parseObject(localStringConf);
        for (LocalCardString cardString : localString.getJSONArray(LocalCardStringKey).toJavaList(LocalCardString.class)) {
            cardString.sn = cardString.sn.toLowerCase();
            if (cardString.image != null) {
                imagePathStringList.add(cardString.image);
            }
            if (!Utils.isNullOrEmpty(cardString.sn)) {
                cardStringMap.put(cardString.sn, cardString);
            }
        }
        for (LocalSkillString skillString : localString.getJSONArray(LocalSkillStringKey).toJavaList(LocalSkillString.class)) {
            skillString.sn = skillString.sn.toLowerCase();
            if (!Utils.isNullOrEmpty(skillString.sn)) {
                skillStringMap.put(skillString.sn, skillString);
            }
        }
        for (LocalUIString uiString : localString.getJSONArray(LocalUIStringKey).toJavaList(LocalUIString.class)) {
            uiString.sn = uiString.sn.toLowerCase();
            if (uiString.images != null) {
                Collections.addAll(imagePathStringList, uiString.images);
            }
            if (!Utils.isNullOrEmpty(uiString.sn)) {
                uiStringMap.put(uiString.sn, uiString);
            }
        }
        Log.res.info("read card string: {}", cardStringMap.size());
        Log.res.info("read skill string: {}", skillStringMap.size());
        Log.res.info("read ui string: {}", uiStringMap.size());
        Log.res.info("read image path string: {}", imagePathStringList.size());
    }

    public static List<LocalImagePathString> getAllImagePathStrings() {
        return imagePathStringList;
    }

    public static LocalCardString getCardString(String sn) {
        if (cardStringMap.containsKey(sn)) {
            return cardStringMap.get(sn);
        }
        Log.res.warn("missing card string:{}", sn);
        return LocalCardString.EmptyString;
    }

    public static LocalSkillString getSkillString(String sn) {
        if (skillStringMap.containsKey(sn)) {
            return skillStringMap.get(sn);
        }
        Log.res.warn("missing skill string:{}", sn);
        return LocalSkillString.EmptyString;
    }

    public static LocalUIString getUIString(String sn) {
        if (uiStringMap.containsKey(sn)) {
            return uiStringMap.get(sn);
        }
        Log.res.warn("missing ui string:{}", sn);
        return LocalUIString.EmptyString;
    }

    public static class LocalCardString {
        public static LocalCardString EmptyString = new LocalCardString();

        public String sn = "[SN]";
        public String name = "[NAME]";
        public CardType cardType = CardType.Data;
        public int cost = 0;
        public int health = 1;
        public String description = "[DESCRIPTION]";
        public String[] skills = null;
        public LocalImagePathString image = null;
    }

    public static class LocalSkillString {
        public static LocalSkillString EmptyString = new LocalSkillString();

        public String sn = "[SN]";
        public String description = "[DESCRIPTION]";
        public String[] strs = new String[]{"[STR0]", "[STR1]", "[STR2]"};
    }

    public static class LocalUIString {
        public static LocalUIString EmptyString = new LocalUIString();

        public String sn = "[SN]";
        public String tip = "[TIP]";
        public String str = "[STR]";
        public String[] strs = new String[]{"[STR0]", "[STR1]", "[STR2]"};
        public LocalImagePathString[] images = null;
    }

    public static class LocalImagePathString {
        public static LocalImagePathString EmptyString = new LocalImagePathString();

        public String path = "[path]";
        public int[] slice;
    }
}
