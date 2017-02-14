//package itn.issuemanager.controller;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.servlet.http.HttpSession;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import itn.issuemanager.config.UserSessionUtils;
//import itn.issuemanager.domain.Comment;
//import itn.issuemanager.domain.Issue;
//import itn.issuemanager.domain.User;
//import itn.issuemanager.repository.CommentRepository;
//import itn.issuemanager.repository.IssuesRepository;
//import itn.issuemanager.support.test.AbstractIntegrationTest;
//
//public class ApiCommentControllerTest extends AbstractIntegrationTest {
//	private static final Logger log = LoggerFactory.getLogger(ApiCommentControllerTest.class);
//
//	@Autowired
//	private IssuesRepository issuesRepository;
//	@Autowired
//	private CommentRepository commentRepository;
//    private Issue issue;
//    private Comment comment;
//    
//    protected MockHttpSession loginSession;
//	protected MockHttpSession noLoginSession;
//	
//	@Before
//	public void setup() {
//	    login("bae", "1234");
//	    User testUser = new User();
//	    testUser.setId((long) 1);
//		testUser.setName("tester");
//		testUser.setUserId("tester@test.com");
//		testUser.setPassword("1234");
//		loginSession = new MockHttpSession();
//		loginSession.setAttribute(UserSessionUtils.USER_SESSION_KEY, testUser);
//		
//	    issue = new Issue("testSubject", "testContents", testUser);
//	    issue = issuesRepository.save(issue);
//	    
//	    Comment commentParam = new Comment();
//	    commentParam.setContents("testCommentContensts");
//	    comment = new Comment(commentParam ,testUser, issue);
//	    comment = commentRepository.save(comment);
//	    }
//
//	@Test
//    public void create() throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        params.add("subject", "testTEST");
//        params.add("contents", "<script> alert('contents');</script>");
//        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
//        ResponseEntity<String> result = template.postForEntity("/api/issue/"+32+"/comment/create", request, String.class);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        log.debug("create body : {}", result.getBody());
//    }
//	
//	@Test
//    public void update() throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        log.debug("comment:{}",comment.toString());
//        log.debug("comment:{}",String.valueOf(comment.getId()));
//        
//        params.add("contents", "testcode");
//        params.add("id", String.valueOf(comment.getId()));
//        params.add("issueId", String.valueOf(issue.getId()));
//        params.add("_method", "put");
//        log.debug("issue:{}",issue.getId());
//        
//        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
//        ResponseEntity<String> result = template.postForEntity("/api/issue/"+132+"/comment/"+267, request, String.class);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        log.debug("update body : {}", result.getBody());
//    }
//	
//	@Test
//    public void delete() throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        params.add("_method", "delete");
//        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
//        ResponseEntity<String> result = template.postForEntity("/api/issue/"+issue.getId()+"/comment/"+comment.getId(), request, String.class);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        log.debug("update body : {}", result.getBody());
//    }
//	
//}
