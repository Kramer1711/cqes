package com.cqjtu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/**
	 * 获得当前具体时间
	 * @return  yyyyMMddhhmmssSSS
	 */
	public static String getNowTime(){
		 SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		 return dateFormatGmt.format(new Date());
	}
}
