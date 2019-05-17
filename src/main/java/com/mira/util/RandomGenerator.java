package com.mira.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class RandomGenerator {

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }

    public static String getRandomString() {
        return getRandomString(64);
    }

    public static int getRandomInt(int min, int max) {
        return RandomUtils.nextInt(min, max);
    }
}
