package com.ej.proxy.utils;

public class PrintUtils {

    private static final boolean DEBUG = false;

    public static void print(String log) {
        if (DEBUG) System.out.println(log);
    }
}
