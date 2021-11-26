package com.backinfile.portal.manager;

import com.backinfile.portal.Log;
import com.backinfile.portal.Res;
import com.backinfile.portal.Settings;
import com.backinfile.portal.model.skills.Skill;
import com.backinfile.support.Utils;
import com.backinfile.support.reflection.ReflectionUtils;
import com.badlogic.gdx.Gdx;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillManager {

    private static final HashMap<String, Class<?>> skillClassMap = new HashMap<>();

    public static void init() {
        List<String> classNameList = new ArrayList<>();
        if (Settings.inDev()) {
            classNameList.addAll(ReflectionUtils.getClassNames(Settings.PACKAGE_NAME));
        } else {
            String str = Gdx.files.internal(Res.CLASSES_FILE_PATH).readString();
            if (!Utils.isNullOrEmpty(str)) {
                for (String name : str.split("\n")) {
                    if (!Utils.isNullOrEmpty(name)) {
                        classNameList.add(name);
                    }
                }
            }
        }

        for (String className : classNameList) {
            try {
                Class<?> clazz = Class.forName(className);
                if (Modifier.isInterface(clazz.getModifiers()) || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                if (Skill.class.isAssignableFrom(clazz)) {
                    skillClassMap.put(clazz.getSimpleName(), clazz);
                    Log.res.info("find skill class:{}", clazz.getSimpleName());
                }
            } catch (ClassNotFoundException e) {
                Log.res.error(e);
            }
        }
    }

    public static Skill build(String sn) {
        if (skillClassMap.containsKey(sn)) {
            try {
                Skill skill = (Skill) skillClassMap.get(sn).getConstructor().newInstance();
                return skill;
            } catch (Exception e) {
                Log.res.error(e);
            }
        }
        Log.res.error("can not find skill class: {}", sn);
        return null;
    }
}
