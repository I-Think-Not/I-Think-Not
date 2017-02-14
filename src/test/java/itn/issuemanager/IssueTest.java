package itn.issuemanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import itn.issuemanager.config.UserSessionUtils;
import itn.issuemanager.domain.User;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("local")
@SpringBootTest
public class IssueTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;
	
	protected MockHttpSession loginSession;
	protected MockHttpSession noLoginSession;
	
	@Before
	public void login() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		User testUser = new User();
		testUser.setId(1L);
		testUser.setName("tester");
		testUser.setUserId("tester@test.com");;
		loginSession = new MockHttpSession();
		loginSession.setAttribute(UserSessionUtils.USER_SESSION_KEY, testUser);
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
//	@Test
//	public void createPageTest() {
//		fail("Not yet implemented");
//	}
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
