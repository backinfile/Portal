package com.backinfile.gdxSupport;

import java.util.HashMap;

import com.backinfile.support.SysException;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class DefaultGame extends Game {
	public static float TimePerFrame = 1f / 60f;
	
	private HashMap<Class<? extends DefaultScreen>, DefaultScreen> screens = new HashMap<>();

	public void setScreen(Class<? extends DefaultScreen> clazz) {
		Screen screen = getScreen(clazz);
		setScreen(screen);
	}

	@SuppressWarnings("unchecked")
	public <T extends DefaultScreen> T getScreen(Class<? extends DefaultScreen> clazz) {
		if (screens.containsKey(clazz)) {
			return (T) screens.get(clazz);
		}
		try {
			DefaultScreen screen = clazz.getConstructor().newInstance();
			screens.put(clazz, screen);
			screen.game = this;
			return (T) screen;
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
}
