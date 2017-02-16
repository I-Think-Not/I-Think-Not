package itn.issuemanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import itn.issuemanager.support.test.AbstractIntegrationTest;

public class UserControllerTest extends AbstractIntegrationTest {
    @Before
    public void setup() {
        login("bae4", "1234");
    }
    
    @Test
    public void form() throws Exception {
        ResponseEntity<String> result = template.getForEntity("/user/new", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().contains("Join Member"));
    }
    
    @Test
    public void join() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        String userId = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        params.add("userId",  userId + "@slipp.net");
        params.add("name", "Jae Sung");
        params.add("password", "password!@");
        HttpEntity<MultiValueMap<String, String>> request = requestForm(params);
        ResponseEntity<String> result = template.postForEntity("/user/join", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

}
