package com.backinfile.support.log;

public class LogManager {
    static ILogAppender localAppender = new ILogAppender.ConsoleAppender();

    public static Logger getLogger(String tag) {
        return new Logger(tag);
    }

    public void setLocalAppender(ILogAppender appender) {
        this.localAppender = appender;
    }
}

