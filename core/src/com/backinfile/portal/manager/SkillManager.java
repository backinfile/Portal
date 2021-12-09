package com.backinfile.portal.manager;

import com.backinfile.portal.Log;
import com.backinfile.portal.Res;
import com.backinfile.portal.Settings;
import com.backinfile.portal.model.Skill;
import com.backinfile.support.Utils;
import com.backinfile.support.reflection.ReflectionUtils;
import com.badlogic.gdx.Gdx;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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

    private static final Pattern intPattern = Pattern.compile("\\d+");
    private static final Pattern floatPattern = Pattern.compile("\\d+\\.\\d+");

    public static Skill build(String sn, String... args) {
        if (skillClassMap.containsKey(sn)) {
            try {
                Class<?> skillClass = skillClassMap.get(sn);
                Class<?>[] argsClass = new Class[args.length];
                Object[] argsValue = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    String arg = args[i];
                    if (intPattern.matcher(arg).matches()) {
                        argsClass[i] = int.class;
                        argsValue[i] = Integer.valueOf(arg);
                    } else if (floatPattern.matcher(arg).matches()) {
                        argsClass[i] = float.class;
                        argsValue[i] = Float.valueOf(arg);
                    } else {
                        argsClass[i] = String.class;
                        argsValue[i] = arg;
                    }
                }
                Skill skill = (Skill) skillClass.getConstructor(argsClass).newInstance(argsValue);
                return skill;
            } catch (Exception e) {
                Log.res.error(e);
            }
        }
        Log.res.error("can not find skill class: {}", sn);
        return null;
    }
}
