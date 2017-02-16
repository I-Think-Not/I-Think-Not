package itn.issuemanager.interceptor;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import itn.issuemanager.config.UserSessionUtils;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.UserRepository;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(BasicAuthInterceptor.class);
    
    @Autowired
    private UserRepository userRepository;
        
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authorization = request.getHeader("Authorization");
        log.debug("Authorization : {}", authorization);
        if (authorization == null || !authorization.startsWith("Basic")) {
            return true;
        }
        
        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                Charset.forName("UTF-8"));
        final String[] values = credentials.split(":",2);
        log.debug("username : {}", values[0]);
        log.debug("password : {}", values[1]);
        
        User user = userRepository.findByUserId(values[0]);
        log.debug("Login Success : {}", user);
        request.getSession().setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return true;
    }
}
