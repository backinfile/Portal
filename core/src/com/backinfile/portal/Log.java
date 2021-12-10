package com.backinfile.portal;

import com.backinfile.support.log.LogManager;
import com.backinfile.support.log.Logger;

public class Log {
    public static final Logger game = LogManager.getLogger("game");
    public static final Logger res = LogManager.getLogger("res");
    
    public static final Logger net = LogManager.getLogger("net");
    public static final Logger server = LogManager.getLogger("net");
    public static final Logger client = LogManager.getLogger("client");
}
