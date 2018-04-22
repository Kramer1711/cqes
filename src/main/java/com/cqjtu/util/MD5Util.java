package com.cqjtu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;
/**
 * 
 * @author 邱凯
 *
 */
public class MD5Util {
	public static String toMD5(String str){
		String md5String = "";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest(str.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			md5String = encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5String;
	}
}
