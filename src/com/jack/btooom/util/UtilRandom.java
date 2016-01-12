package com.jack.btooom.util;

import java.util.Random;

public class UtilRandom {

	private static final Random random = new Random();
	private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String getRandomString(int length) {
		StringBuilder b = new StringBuilder(length);
		for (int j = 0; j < length; j++)
			b.append(chars.charAt(random.nextInt(chars.length())));
		return b.toString();
	}
}
