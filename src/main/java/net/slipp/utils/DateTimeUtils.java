package net.slipp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
}
