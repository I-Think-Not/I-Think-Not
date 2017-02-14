package itn.issuemanager.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("itn.issuemanager.service.fileservice")
public class FileServiceProperties {

	private String location = "file";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
