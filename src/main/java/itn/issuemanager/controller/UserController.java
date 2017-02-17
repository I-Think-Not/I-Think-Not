package itn.issuemanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.config.UserSessionUtils;
import itn.issuemanager.domain.ForbiddenTypeException;
import itn.issuemanager.domain.ForbiddenTypeFileException;
import itn.issuemanager.domain.UploadFile;
import itn.issuemanager.domain.User;
import itn.issuemanager.domain.ValidationError;
import itn.issuemanager.repository.UserRepository;
import itn.issuemanager.service.FileService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private FileService fileService;
	@Autowired
	private UserRepository userRepository;

	// 회원가입 페이지
	@GetMapping("/new")
	public String form() {
		return "/user/join";
	}

	// 회원가입 기능
	@PostMapping("/join")
	public String create(@Valid User user, BindingResult bindingResult, Model model, MultipartFile picture) throws IOException, ForbiddenTypeFileException {
		log.debug("User :" + user.toString());
		try{
		    // TODO 이 부분을 별도의 메소드로 분리해서 처리할 수 없을까?
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
			log.debug(user.getPassword());
			user.setEncryptPassword();
			userRepository.save(user);
      UploadFile profile = fileService.imageUpload(picture);
      user.setProfile(profile);
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
	public String edit(@LoginUser User loginUser,@PathVariable long id, Model model) throws ForbiddenTypeException {
		User user = userRepository.findOne(id);
		if(!loginUser.isSameUser(user)){
			throw new ForbiddenTypeException();
		}
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	// 회원수정 메소드
	@PutMapping("/{id}")
	public String update(@LoginUser User loginUser,@PathVariable long id, User updatedUser, String newPassword) throws ForbiddenTypeException {
		User user = userRepository.findOne(id);
		if (!loginUser.isSameUser(user)) {
			throw new ForbiddenTypeException();
		}
		if(user.isPassword(updatedUser.getPassword()))
			log.debug("기존 비밀번호가 틀렸습니다.");
		  
		user.update(updatedUser, newPassword);
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
