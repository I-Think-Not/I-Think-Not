package itn.issuemanager.config;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpConfig {
	
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	public static MimeMessage msgConfig(Session session, String fromMail, String toEmail, String tempPwd){
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromMail,"스마일게이트"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail,"패스워드 찾는분"));
			msg.setSubject("임시 비밀번호 입니다.");
			msg.setContent(tempPwd,"text/html; charset=utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msg;
	}
	
	public static Properties initSmtpConfig(){
		
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
	    props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		
		return props;
	}
	
}
