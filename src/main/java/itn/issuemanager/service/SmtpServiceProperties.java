package itn.issuemanager.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import itn.issuemanager.utils.PasswordUtils;

@ConfigurationProperties("itn.issuemanager.service.smtpservice")
@Component
public class SmtpServiceProperties {

	private String fromMail="clearpaltemp@gmail.com";
	private String mailId = "clearpaltemp";
	private String mailPwd ="tmakdlfrpdlxm";
	private String mailTitle="임시비밀번호 입니다.";
	private String tempPwd = "tempPwd";
	
	public SmtpServiceProperties(){
		this.setTempPwd(PasswordUtils.tempPassword());
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getMailPwd() {
		return mailPwd;
	}

	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getTempPwd() {
		return tempPwd;
	}

	public void setTempPwd(String tempPwd) {
		this.tempPwd = tempPwd;
	}
	
}
