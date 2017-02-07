package itn.issuemanager.config;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.WebRequest;

import itn.issuemanager.domain.User;

public class UserSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";
	
	public static User getUserFromSession(HttpSession session){
		Object user = session.getAttribute(USER_SESSION_KEY);
		if(user == null){
			return null;
		}
		return (User)user;
	}
	
	public static User getUser(WebRequest webRequest){
		Object user = webRequest.getAttribute(USER_SESSION_KEY, webRequest.SCOPE_SESSION);
		if(user == null){
			return null;
		}
		return (User)user;
	}
	
	public static boolean isLogined(HttpSession session){
		if(getUserFromSession(session) == null){
			return false;
		}
		return true;
	}
	
	public boolean isSameUser(HttpSession session, User user){
		if(!isLogined(session)){
			return false;
		}
		
		if(user == null){
			return false;
		}
		
		return user.isSameUser(getUserFromSession(session));
	}
}
