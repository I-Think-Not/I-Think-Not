package itn.issuemanager.utils;

import java.util.UUID;

public class PasswordUtils {
	
	public static String tempPassword(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		uuid = uuid.substring(0, 9);
		
		return uuid+"!";
	}

}
