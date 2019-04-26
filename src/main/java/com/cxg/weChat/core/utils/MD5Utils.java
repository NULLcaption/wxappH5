package com.cxg.weChat.core.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.MessageDigest;
/**
* @Description:    MD5加密工具
* @Author:         Cheney Master
* @CreateDate:     2018/11/14 9:23
* @Version:        1.0
*/
public class MD5Utils {
	private static final String SALT = "1qazxsw2";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String md5Encry(String strSrc) throws Exception {
		String returnStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			returnStr = byteArrayToHexString(md5.digest(strSrc.getBytes()));
		} catch (Exception e) {
			throw new Exception(e);
		}

		if (returnStr == null) {
			throw new Exception("md5Encry null result");
		}
		return returnStr;
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];
	}

}
