package itn.issuemanager.controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.MilestoneRepository;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.support.test.AbstractIntegrationTest;
import itn.issuemanager.support.test.HtmlFormDataBuilder;

public class ApiMilestoneControllerTest extends AbstractIntegrationTest{
	private static final Logger log = LoggerFactory.getLogger(ApiMilestoneControllerTest.class);
	@Autowired
	private MilestoneRepository milestoneRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private Milestone milestone;
	private User user;
	
	@Before
	public void setup(){
		user = userRepository.findOne(1L);
		login(user.getUserId(),user.getPassword());
		
		milestone = new Milestone("tester",new Date(),new Date());
		milestoneRepository.save(milestone);
	}
	
	@Test
	public void openIssueList(){
		 HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
		            .urlEncodedForm()
		            .addParameter("id", milestone.getId())
		            .build();
		
		ResponseEntity<String> result = template.postForEntity("/milestone/api/" + milestone.getId() 
		+ "/openIssues", request, String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		 log.debug("create body : {}", result.getBody());
	}
	
	@Test
	public void closeIssueList(){
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
	            .urlEncodedForm()
	            .addParameter("id", milestone.getId())
	            .build();
		
		ResponseEntity<String> result = template.postForEntity("/milestone/api/" + milestone.getId() 
		+ "/closeIssues", request, String.class);
		 assertEquals(HttpStatus.OK, result.getStatusCode());
	     log.debug("update body : {}", result.getStatusCode());
	        
	}
	
}
