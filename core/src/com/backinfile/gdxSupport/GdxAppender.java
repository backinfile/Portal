package com.backinfile.gdxSupport;

import com.backinfile.support.Utils;
import com.backinfile.support.log.ILogAppender;
import com.backinfile.support.log.LogLevel;
import com.badlogic.gdx.Gdx;

public class GdxAppender implements ILogAppender {
    @Override
    public void append(LogLevel logLevel, String tag, String message, Object[] args, Throwable throwable) {
        if (Gdx.app == null) {
            ConsoleAppender.Instance.append(logLevel, tag, message, args, throwable);
            return;
        }
        
        if (Utils.isNullOrEmpty(message)) {
            message = "";
        }
        String msg = args == null ? message : Utils.format(message, args);

        switch (logLevel) {
            case INFO:
            case WARN:
                Gdx.app.log(tag, msg);
                break;
            case ERROR:
                if (throwable == null) {
                    Gdx.app.error(tag, msg);
                } else {
                    Gdx.app.error(tag, msg, throwable);
                }
                break;
            case NONE:
                break;
        }
    }
}
