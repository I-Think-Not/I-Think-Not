package itn.issuemanager.controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.support.test.AbstractIntegrationTest;
import itn.issuemanager.support.test.HtmlFormDataBuilder;

public class MilestoneControllerTest extends AbstractIntegrationTest{

	@Autowired
	private UserRepository userRepository;
	
	private Milestone milestone;
	private User user;
	
	@Before
	public void setup() {
		user = userRepository.findOne(1L);
		login(user.getUserId(),user.getPassword());
	}
	
	@Test
	public void create() {
		 HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				 .urlEncodedForm()
				 .addParameter("subject", "test")
				 .addParameter("startDate", "2017-02-01")
				 .addParameter("endDate", "2017-02-03")
				 .build();
		 ResponseEntity<String> result = template.postForEntity("/milestone/create", request, String.class);
		 
		 assertEquals(HttpStatus.FOUND,result.getStatusCode());
	    
		 
	}
}
