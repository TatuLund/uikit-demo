package org.vaadin.uikit.components.util;

import java.io.Serializable;
import java.util.Random;

@SuppressWarnings("serial")
public class Utils implements Serializable {
    private static Random rand = new Random();

    public static String randomKey(int chars) {
        int limit = (int) (Math.pow(10, chars) - 1);
        String key = "" + rand.nextInt(limit);
        key = String.format("%" + chars + "s", key).replace(' ', '0');
        return key;
    }
}
