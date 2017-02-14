package itn.issuemanager.config;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	
	private String id;
	
	private String pwd;
	
	public SMTPAuthenticator(String id,String pwd){
		this.id = id;
		this.pwd = pwd;
	}
	
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication("clearpaltemp@gmail.com","tmakdlfrpdlxm");
	}
}
