package itn.issuemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import itn.issuemanager.repository.IssuesRepository;

@Controller
public class IndexController {

	@Autowired
	private IssuesRepository issuesRepository;
	
	@GetMapping("/")	//issue목록 보여주기
	public String index(Model model) {
	    // TODO 이슈 목록을 어떤 기준으로 정렬 또는 Opened 상태 등등 고려해 목록을 가져온다.
		model.addAttribute("issues", issuesRepository.findAll());
		return "index";
	}
}
