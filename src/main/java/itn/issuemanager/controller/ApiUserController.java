package itn.issuemanager.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.SMTPAuthenticator;
import itn.issuemanager.config.SmtpConfig;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.service.SmtpServiceProperties;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SmtpServiceProperties smtpServiceProperties;
	
	// TODO 요청이 POST인 이유가 있는가? GET으로 하면 안되는 이유는?
	// TODO 메소드 이름에 _를 사용하지 않는다. camel convention을 따른다.
	@PostMapping("/idCheck")
	public boolean idCheck(String id){
	    // TODO 다음 코드를 return userRepository.findByUserId(id) == null;로 구현한다면...
		return userRepository.findByUserId(id) == null;
	}
	
	@PostMapping("/findPw")
	public boolean findPw(String toEmail){
		
		String tempPwd=smtpServiceProperties.getTempPwd();
		String id = smtpServiceProperties.getMailId();
		String pwd =smtpServiceProperties.getMailPwd();
		String title = smtpServiceProperties.getMailTitle();
		String fromMail = smtpServiceProperties.getFromMail();
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
			user.setEncryptPassword();
			userRepository.save(user);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
}
