package itn.issuemanager.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")	//issue목록
	public String index(HttpSession session) {
		
		return "index";
	}
}
