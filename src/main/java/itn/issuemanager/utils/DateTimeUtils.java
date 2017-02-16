package itn.issuemanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.KOREA);
		return sdf.format(date);
	}
}
