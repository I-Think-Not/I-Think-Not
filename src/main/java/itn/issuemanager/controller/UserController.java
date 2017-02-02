package itn.issuemanager.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.User;
import itn.issuemanager.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private final String USER_SESSION_KEY = "sessionedUser";
	
	@Autowired
	private UserRepository userRepository;
	
	
	//회원가입 페이지
	@GetMapping("/new")
	public String form(){
		return "/user/join";
	}
	//회원가입 기능
	@PostMapping("/join")
	public String create(User user){
		log.debug("User :" + user.toString());
		userRepository.save(user);
		return "redirect:/users/login";
	}
	//회원 로그인
	@GetMapping("/login")
	public String login(String userId, String password, HttpSession session){
		return "/user/login";
	}
	//회원수정 페이지
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable long id, Model model, HttpSession session){
		if(isLoginUser(session)){
			return "redirect:/users/login";
		}
		
		User sessionedUser = getUserFromSession(session);
		if(!sessionedUser.matchedId(id)){
			throw new IllegalStateException("You can't update the anther user");
		}
		User user = userRepository.findOne(id);
		model.addAttribute("user",user);
		return "/user/updateform";
	}
	//회원수정 메소드
	@PutMapping("/{id}")
	public String update(@PathVariable long id, User updatedUser, HttpSession session){
		if(isLoginUser(session)){
			return "redirect:/users/login";
		}
		
		User sessionedUser = getUserFromSession(session);
		if (!sessionedUser.matchedId(id)) {
			throw new IllegalStateException("You can't update the anther user");
		}
		
		User user = userRepository.findOne(id);
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	//회원삭제 메소드
	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id){
		
		return "redirect:/users";
	}
	//로그인 여부 확인
	public boolean isLoginUser(HttpSession session){
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionedUser == null){
			return false;
		}
		return true;
	}
	//세션으로부터 User 가져오기
	public User getUserFromSession(HttpSession session){
		if(!isLoginUser(session)){
			return null;
		}
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
	
	
	
	
}
