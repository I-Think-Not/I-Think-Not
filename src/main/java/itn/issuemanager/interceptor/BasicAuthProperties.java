package itn.issuemanager.interceptor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("itn.issuemanager.interceptor.basicauth")
public class BasicAuthProperties {
	private boolean basicAuthEnable = true;

	public boolean isBasicAuthEnable() {
		return basicAuthEnable;
	}

	public void setBasicAuthEnable(boolean basicAuthEnable) {
		this.basicAuthEnable = basicAuthEnable;
	}

	
}
