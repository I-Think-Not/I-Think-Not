package itn.issuemanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.config.UserSessionUtils;
import itn.issuemanager.domain.User;
import itn.issuemanager.domain.ValidationError;
import itn.issuemanager.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private UserRepository userRepository;

	// 회원가입 페이지
	@GetMapping("/new")
	public String form() {
		return "/user/join";
	}

	// 회원가입 기능
	@PostMapping("/join")
	public String create(@Valid User user, BindingResult bindingResult, Model model){
		log.debug("User :" + user.toString());
		try{
			if(bindingResult.hasErrors()){
				List<FieldError> errors = bindingResult.getFieldErrors();
				List<ValidationError> vError =new ArrayList<ValidationError>();
				for(FieldError error : errors){
					vError.add(new ValidationError(error.getField(),error.getDefaultMessage()));
					log.debug("error : "+error.getField()+" "+error.getDefaultMessage());
				}
				model.addAttribute("vError",vError);
				return "/user/join";
			}
			userRepository.save(user);
		}catch (DataAccessException ex) {
           log.debug(ex.getCause().getMessage());
       	   return "/user/join";
        }
		return "redirect:/user/login";
	} 

	@GetMapping("/login")
	public String loginForm() {
		return "/user/login";
	}

	// 회원 로그인
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			log.debug("Login Failure");
			return "redirect:/user/login";
		}

		if (!user.isPassword(password)) {
			log.debug("Login Failure");
			return "redirect:/user/login";
		}
		log.debug("Login Success : ", user.toString());
		session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
	}

	// 회원수정 페이지
	@GetMapping("/{id}/edit")
	public String edit(@LoginUser User loginUser,@PathVariable long id, Model model) {
		User user = userRepository.findOne(id);
		if(!loginUser.isSameUser(user)){
			throw new IllegalStateException("You can't update the anther user");
		}
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	// 회원수정 메소드
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser,@PathVariable long id, User updatedUser) {
		User user = userRepository.findOne(id);
		if (!loginUser.isSameUser(user)) {
			throw new IllegalStateException("You can't update the anther user");
		}
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/";
	}

	// 회원 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
		return "redirect:/user/login";
	}

	// 회원삭제 메소드
	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id) {

		return "redirect:/user";
	}
	
	@GetMapping("/findPw")
	public String findPwForm(){
		return "/user/findPw";
	}
	
}
