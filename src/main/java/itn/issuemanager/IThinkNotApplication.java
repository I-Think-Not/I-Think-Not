package itn.issuemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import itn.issuemanager.interceptor.BasicAuthProperties;
import itn.issuemanager.service.FileService;
import itn.issuemanager.service.FileServiceProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileServiceProperties.class)
public class IThinkNotApplication {

	public static void main(String[] args) {
		SpringApplication.run(IThinkNotApplication.class, args);
	}
}
