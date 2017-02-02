package IThinkNot.IssueManager.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import IThinkNot.IssueManager.domain.User;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	//초기 페이지
	@GetMapping("")
	public String index(){
		return "/index";
	}
	//회원가입 페이지
	@GetMapping("/new")
	public String form(){
		return "/user/join";
	}
	//회원가입 기능
	@PostMapping("/join")
	public String create(User user){
		//log.debug(user);
		//userRepository.save(user);
		return "redirect:/users";
	}
	//회원 로그인
	@GetMapping("/login")
	public String login(){
		return "/user/login";
	}
	//회원수정 페이지
	@GetMapping("/{id}/edit")
	public String edit(){
		
		return "/user/updateform";
	}
	//회원수정 메소드
	@PutMapping("/{id}")
	public String update(@PathVariable long id, User user, HttpSession session){
		
		return "redirect:/users";
	}
	//회원삭제 메소드
	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id){
		
		return "redirect:/users";
	}
	
	
}
