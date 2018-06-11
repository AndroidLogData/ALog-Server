package com.logdata.common.logger;

import org.slf4j.LoggerFactory;

import java.nio.file.AccessDeniedException;

public class Logger {
    private static boolean showLog;

    private Logger() {
        try {
            throw new AccessDeniedException("Access Denied Exception");
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }
    }

    private static class Singleton {
        private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class.getClass());
    }

    public static void showLog(boolean flag) {
        Logger.showLog = flag;
    }

    public static void i(Object message) {
        if (showLog) {
            Singleton.LOGGER.info(message.toString());
        }
    }

    public static void i(Object message, Throwable t) {
        if (showLog) {
            Singleton.LOGGER.info(message.toString(), t);
        }
    }

    public static void d(Object message) {
        if (showLog) {
            Singleton.LOGGER.debug(message.toString());
        }
    }

    public static void d(Object message, Throwable t) {
        if (showLog) {
            Singleton.LOGGER.debug(message.toString(), t);
        }
    }

    public static void w(Object message) {
        if (showLog) {
            Singleton.LOGGER.warn(message.toString());
        }
    }

    public static void w(Object message, Throwable t) {
        if (showLog) {
            Singleton.LOGGER.warn(message.toString(), t);
        }
    }

    public static void e(Object message) {
        if (showLog) {
            Singleton.LOGGER.error(message.toString());
        }
    }

    public static void e(Object message, Throwable t) {
        if (showLog) {
            Singleton.LOGGER.error(message.toString(), t);
        }
    }
}
