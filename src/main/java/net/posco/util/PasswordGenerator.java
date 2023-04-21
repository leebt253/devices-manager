package net.posco.util;

import java.util.Random;

public class PasswordGenerator {
	
	public String generatePassword(Integer len) {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String num = "0123456789";
		String special = "@#&*";
		String combination = upper + lower + num + special;
		char[] password = new char[len];
		Random random = new Random();
		for (int i=0; i<len; i++) {
			password[i]=combination.charAt(random.nextInt(combination.length()));
		}
		return new String(password);
	}
	
}
