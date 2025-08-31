package fr.erpriex.wakeproxy.utils;

public class StringUtils {

    public static String stringOrNull(Object o) {
        return (o instanceof String s) ? s : null;
    }

}
