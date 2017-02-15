package itn.issuemanager.controller;

import java.util.Collections;
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
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);

	@GetMapping("/")	//issue목록 보여주기
	public String index(Model model) {
	    // TODO 이슈 목록을 어떤 기준으로 정렬 또는 Opened 상태 등등 고려해 목록을 가져온다.
		List<Issue> closedIssues = issuesRepository.findByState(IssueState.CLOSED);
		List<Issue> openIssues = issuesRepository.findByState(IssueState.OPEN);
		Collections.reverse(openIssues);
		Collections.reverse(closedIssues);
		model.addAttribute("closedIssues", closedIssues);
		model.addAttribute("openIssues", openIssues);
	
		return "index";
	}
}
