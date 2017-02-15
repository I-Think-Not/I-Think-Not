package itn.issuemanager;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import itn.issuemanager.config.UserSessionUtils;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@SpringBootTest
public class IssueTest {
	
	private static final Logger log = LoggerFactory.getLogger(IssueTest.class);
	
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private UserRepository userRepository;
	
	private MockMvc mvc;
	
	protected MockHttpSession noLoginSession;
	protected MockHttpSession loginSession;
	protected MockHttpSession writerSession;
	
	@Before
	public void login() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		User testUser;
		User writer;
		
		testUser = userRepository.findOne(1L);
		writer = userRepository.findOne(2L);
		log.debug(testUser.toString());
		
		loginSession = new MockHttpSession();
		loginSession.setAttribute(UserSessionUtils.USER_SESSION_KEY, testUser);
		writerSession = new MockHttpSession();
		writerSession.setAttribute(UserSessionUtils.USER_SESSION_KEY, writer);
		noLoginSession = new MockHttpSession();
	}
	@Test
	public void indexPageTest() throws Exception {
		mvc.perform(get("/").session(noLoginSession)).andExpect(status().is2xxSuccessful());
	}
	@Test
	public void newPageTest() throws Exception {
		mvc.perform(get("/issue/new").session(noLoginSession)).andExpect(status().is3xxRedirection());
		mvc.perform(get("/issue/new").session(loginSession)).andExpect(status().is2xxSuccessful());
	}
	@Test
	public void createPageTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        
        Issue issue = new Issue();
        
        issue.setSubject("testIssue");
        issue.setContents("testtesttest");
        
        params.add("subject", issue.getSubject());
        params.add("contents", issue.getContents());
		
        mvc.perform(post("/issue/").session(noLoginSession).params(params)).andExpect(status().is3xxRedirection());
		mvc.perform(post("/issue/").session(writerSession).params(params)).andExpect(status().is3xxRedirection());
		
		issue.setWriter((User)writerSession.getAttribute(UserSessionUtils.USER_SESSION_KEY));
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues();
		Example<Issue> example = Example.of(issue,matcher);
		
		Issue inserted = issuesRepository.findOne(example);
		if(inserted == null)
			fail("not inserted!!");
		
	}
//	@Test
//	public void showPageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void editPageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void updatePageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void deletePageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void setMilestonePageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void setLabelPageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void setClosePageTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void setOpenPageTest() {
//		fail("Not yet implemented");
//	}
}
