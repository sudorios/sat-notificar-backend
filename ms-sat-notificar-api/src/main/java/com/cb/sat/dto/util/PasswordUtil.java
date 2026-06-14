package com.cb.sat.dto.util;

public class PasswordUtil {

	private static final String NUMEROS = "0123456789";
	private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	private PasswordUtil() {
	}

	/**
	 * Password de 8 Caracteres
	 * 
	 * @return
	 */
	public static String getPassword() {
		return getPassword(8);
	}

	/**
	 * Password de N Caracteres
	 * 
	 * @param length
	 * @return
	 */
	public static String getPassword(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}

	/**
	 * Password de N Caracteres con semilla
	 * 
	 * @param length
	 * @return
	 */
	public static String getPassword(String key, int length) {
		String pswd = "";
		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}
		return pswd;
	}

}
