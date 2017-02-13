package net.slipp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO 중복 클래스. 제거 필요함.
public class DateTimeUtils {
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
}
