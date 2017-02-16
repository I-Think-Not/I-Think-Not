package itn.issuemanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssueState;
import itn.issuemanager.repository.IssuesRepository;

@Controller
public class IndexController {

	@Autowired
	private IssuesRepository issuesRepository;
	
    // TODO 사용하지 않는 코드 제거한다.
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);

	@GetMapping("/")	//issue목록 보여주기
	public String index(Model model) {
		List<Issue> closedIssues = issuesRepository.findByStateOrderByCreationDateDesc(IssueState.CLOSED);
		List<Issue> openIssues = issuesRepository.findByStateOrderByCreationDateDesc(IssueState.OPEN);
		model.addAttribute("closedIssues", closedIssues);
		model.addAttribute("openIssues", openIssues);
		return "index";
	}
}
