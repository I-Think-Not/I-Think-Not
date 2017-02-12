package itn.issuemanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import itn.issuemanager.support.test.AbstractIntegrationTest;

public class ApiCommentControllerTest extends AbstractIntegrationTest {
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);

	@Test
    public void create() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("subject", "testTEST");
        params.add("contents", "<script> alert('contents');</script>");
        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
        ResponseEntity<String> result = template.postForEntity("/api/issue/"+43+"/comment/create", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }
	
	@Test
    public void update() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("contents", "tescode");
        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
        ResponseEntity<String> result = template.postForEntity("/api/issue/"+43+"/comment/"+1, request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }
}
