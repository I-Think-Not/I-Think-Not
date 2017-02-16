package itn.issuemanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import itn.issuemanager.config.UserSessionUtils;
import itn.issuemanager.domain.User;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests {

	@Autowired
	private MockMvc mvc;
	
	
	
	protected MockHttpSession session;	
	
	@Before
	public void login() throws Exception {
		User testUser = new User();
		testUser.setId(1L);
		testUser.setName("tester");
		testUser.setUserId("tester@test.com");;
		session = new MockHttpSession();
		session.setAttribute(UserSessionUtils.USER_SESSION_KEY, testUser);
	}
	
	@Test
	public void uploadTest() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt","text/plain","hello".getBytes());
		this.mvc.perform(fileUpload("/api/file/").file(multipartFile).session(session))
		.andExpect(status().is2xxSuccessful());		
	}
}
