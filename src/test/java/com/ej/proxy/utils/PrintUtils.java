package com.ej.proxy.utils;

public class PrintUtils {

    private static final boolean DEBUG = true;

    public static void print(String log) {
        if (DEBUG) System.out.println(log);
    }
}
