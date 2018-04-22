package com.cqjtu.util;

import java.util.Random;

/**
 * 
 * @author 邱凯
 *
 */
public class TokenUtil {

	private static TokenUtil tokenUtil;

	private TokenUtil() {
	}

	public static TokenUtil getInstance() {
		if (tokenUtil == null)
			tokenUtil = new TokenUtil();
		return tokenUtil;
	}

	public String makeToken() {
		String token = System.currentTimeMillis() + new Random().nextInt() + "";
		// 利用MD5摘要算法得到固定长度的字符串
		return MD5Util.toMD5(token);
	}
}
