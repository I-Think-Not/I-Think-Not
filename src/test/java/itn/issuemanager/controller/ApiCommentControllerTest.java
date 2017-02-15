package itn.issuemanager.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.support.test.AbstractIntegrationTest;

public class ApiCommentControllerTest extends AbstractIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(ApiCommentControllerTest.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssuesRepository issuesRepository;
    @Autowired
    private CommentRepository commentRepository;
    
    private User testUser;
    private Issue issue;

    @Before
    public void setup() {
        testUser = new User();
        testUser.setName("tester");
        testUser.setUserId("tester@test.com");
        testUser.setPassword("123456!@");
        testUser = userRepository.save(testUser);
        log.debug("user : {}", testUser);
        
        login(testUser.getUserId(), testUser.getPassword());
        
        issue = new Issue("testSubject", "testContents", testUser);
        issue = issuesRepository.save(issue);
    }

    @Test
    public void create() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("subject", "testTEST");
        params.add("contents", "<script> alert('contents');</script>");
        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
        ResponseEntity<String> result = template.postForEntity("/api/issue/" + issue.getId() + "/comment/create",
                request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("create body : {}", result.getBody());
    }

    @Test
    public void update() throws Exception {
        Comment commentParam = new Comment();
        commentParam.setContents("testCommentContensts");
        Comment comment = new Comment(commentParam ,testUser, issue);
        comment = commentRepository.save(comment);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        log.debug("comment:{}", comment.toString());
        log.debug("comment:{}", String.valueOf(comment.getId()));

        params.add("contents", "testcode");
        params.add("id", String.valueOf(comment.getId()));
        params.add("issueId", String.valueOf(issue.getId()));
        params.add("_method", "put");
        log.debug("issue:{}", issue.getId());

        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
        ResponseEntity<String> result = template.postForEntity("/api/issue/" + issue.getId() + "/comment/" + commentParam.getId(), request,
                String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("update body : {}", result.getBody());
    }

    @Test
    public void delete() throws Exception {
        Comment commentParam = new Comment();
        commentParam.setContents("testCommentContensts");
        Comment comment = new Comment(commentParam ,testUser, issue);
        comment = commentRepository.save(comment);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("_method", "delete");
        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
        ResponseEntity<String> result = template
                .postForEntity("/api/issue/" + issue.getId() + "/comment/" + comment.getId(), request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("update body : {}", result.getBody());
    }
}
