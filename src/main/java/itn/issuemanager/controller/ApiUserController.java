package itn.issuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.domain.User;
import itn.issuemanager.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/id_check")
	public String id_check(String id){
		User user = userRepository.findByUserId(id);
		if(user!=null){
			log.debug(user.toString());
			return "no";
		}
		return "ok";
	}
}
