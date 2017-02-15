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
import org.springframework.util.MultiValueMap;

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.support.test.AbstractIntegrationTest;
import itn.issuemanager.support.test.HtmlFormDataBuilder;

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
        testUser = userRepository.findOne(1L);
        login(testUser.getUserId(), testUser.getPassword());

        issue = new Issue("testSubject", "testContents", testUser);
        issue = issuesRepository.save(issue);
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
            .urlEncodedForm()
            .addParameter("subject", "testTEST")
            .addParameter("contents", "<script> alert('contents');</script>")
            .build();

        ResponseEntity<String> result = template.postForEntity("/api/issue/" + issue.getId() + "/comment/create",
                request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("create body : {}", result.getBody());
    }

    @Test
    public void update() throws Exception {
        Comment comment = createComment();
        
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .urlEncodedForm()
                .addParameter("id", comment.getId())
                .addParameter("issueId", issue.getId())
                .addParameter("contents", "testcode")
                .put()
                .build();

        log.debug("issue:{}", issue.getId());

        ResponseEntity<String> result = template.postForEntity("/api/issue/" + issue.getId() + "/comment/" + comment.getId(), request,
                String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("update body : {}", result.getBody());
    }

    @Test
    public void delete() throws Exception {
        Comment comment = createComment();
        
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .urlEncodedForm()
                .delete()
                .build();

        ResponseEntity<String> result = template
                .postForEntity("/api/issue/" + issue.getId() + "/comment/" + comment.getId(), request, String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("update body : {}", result.getBody());
    }

    
	private Comment createComment() {
		Comment commentParam = new Comment();
        commentParam.setContents("testCommentContensts");
        Comment comment = new Comment(commentParam ,testUser, issue);
        comment = commentRepository.save(comment);
		return comment;
	}
}
