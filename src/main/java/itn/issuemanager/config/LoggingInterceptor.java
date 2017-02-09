package itn.issuemanager.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import itn.issuemanager.domain.User;

public class LoggingInterceptor  extends HandlerInterceptorAdapter{

	private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user =(User)session.getAttribute("sessionedUser");
		if(user == null || user.getId()==null){
			log.debug("no login");
			response.sendRedirect("/user/login");
			return false;
		}
		return true;
	}
}