package com.backinfile.support;

import com.badlogic.gdx.Gdx;

public class Log {
    public static Logger core = new Logger("CORE");

    public static Logger timer = new Logger("TIMER");
    public static Logger reflection = new Logger("REFLECTION");
    public static Logger invoke = new Logger("INVOKE");

    public static Logger res = new Logger("RES");
    public static Logger client = new Logger("CLIENT");
    public static Logger server = new Logger("SERVER");
    public static Logger net = new Logger("NET");
    public static Logger view = new Logger("VIEW");

    public static Logger game = new Logger("GAME");
    public static Logger level = new Logger("LEVEL");
    public static Logger gameLog = new Logger("GAMELOG");

    public static class Logger {
        private String name;

        public Logger(String name) {
            this.name = name.toUpperCase();
        }

        public void info(String msg, Object... args) {
            if (Gdx.app != null) {
                Gdx.app.log(name, Utils.format(msg, args));
            } else {
                System.out.println(Utils.format("[{}] {}", name, Utils.format(msg, args)));
            }
        }

        public void error(String msg, Object... args) {
            if (Gdx.app != null) {
                Gdx.app.error(name, Utils.format(msg, args));
            } else {
                System.out.println(Utils.format("[{}] {}", name, Utils.format(msg, args)));
            }
        }

        public void error(Throwable e) {
            if (Gdx.app != null) {
                Gdx.app.error(name, "", e);
            } else {
                System.out.println(e);
            }
        }

        public void warn(String msg, Object... args) {
            if (Gdx.app != null) {
                Gdx.app.log(name, Utils.format(msg, args));
            } else {
                System.out.println(Utils.format("[{}] {}", name, Utils.format(msg, args)));
            }
        }
    }
}
