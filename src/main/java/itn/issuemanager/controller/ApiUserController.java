package itn.issuemanager.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.SMTPAuthenticator;
import itn.issuemanager.config.SmtpConfig;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.utils.PasswordUtils;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/id_check")
	public boolean id_check(String id){
		User user = userRepository.findByUserId(id);
		if(user!=null){
			log.debug(user.toString());
			return false;
		}
		return true;
	}
	
	
	@PostMapping("/findPw")
	public boolean findPw(String toEmail){
		String tempPwd=PasswordUtils.tempPassword();
		String id = "clearpaltemp";
		String pwd ="tmakdlfrpdlxm";
		String title="임시비밀번호 입니다.";
		String fromMail="clearpaltemp@gmail.com";
		
		User user = userRepository.findByUserId(toEmail);
		
		if(user == null){
			log.debug("유저가 존재하지 않습니다.");
			return false;
		}
		
		Properties props = SmtpConfig.initSmtpConfig();
		
		log.debug(toEmail);
		
		try{
			
			Authenticator auth = new SMTPAuthenticator(id,pwd);
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);
			MimeMessage msg = SmtpConfig.msgConfig(session, fromMail, toEmail, tempPwd);
		
			Transport.send(msg);
			user.setPassword(tempPwd);
			log.debug("aa : {}",user);
			userRepository.save(user);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
}
