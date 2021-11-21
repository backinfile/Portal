package com.backinfile.portal;

import com.backinfile.gdxSupport.DefaultGame;
import com.backinfile.portal.view.screens.TestScreen;

public class MainGame extends DefaultGame {
    @Override
    public void create() {
        setScreen(TestScreen.class);
    }
}
