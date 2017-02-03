package itn.issuemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import itn.issuemanager.domain.IssuesRepository;

@Controller
public class IndexController {

	@Autowired
	private IssuesRepository issuesRepository;
	
	@GetMapping("/")	//issue목록 보여주기
	public String index(Model model) {
		model.addAttribute("issues", issuesRepository.findAll());
		return "index";
	}
}
