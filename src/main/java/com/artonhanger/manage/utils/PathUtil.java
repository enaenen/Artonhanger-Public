package com.artonhanger.manage.utils;

public class PathUtil {
    private PathUtil() { }

    public static String replaceWindowPathToLinuxPath(String path) {
        return path.replaceAll("\\\\", "/");
    }
}
