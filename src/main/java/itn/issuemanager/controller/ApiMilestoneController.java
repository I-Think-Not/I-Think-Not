package itn.issuemanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssueState;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.MilestoneRepository;

@RestController
// TODO url의 일관성을 맞춘다. ApiLableController, ApiLableController는 /api로 시작한다.
@RequestMapping("/milestone/api/{id}")
public class ApiMilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestonesController.class);
	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private MilestoneRepository milestoneRepository; 
	
	// TODO openIssueList와 closeIssueList에 많은 중복이 있다. 이 두 개의 Controller 메소드를 하나로 통합을 시도해 본다.
	@PostMapping("/openIssues") ///{issueId}/setMilestone/{milestoneId}
	public List<Issue> openIssueList(@PathVariable Long id,Model model){
		Milestone milestone = milestoneRepository.findOne(id);
		List<Issue> openIssues = issuesRepository.findByMilestoneAndState(milestone, IssueState.OPEN);
		log.debug("openIssueList"+openIssues.size());
		
		model.addAttribute("Issues", openIssues);
		// TODO 굳이 필요없는 로그로 보여진다.
		log.debug("openIssueList");
		return openIssues;
	}
	
	@PostMapping("/closeIssues") ///{issueId}/setMilestone/{milestoneId}
	public List<Issue> closeIssueList(@PathVariable Long id,Model model){
		Milestone milestone = milestoneRepository.findOne(id);
		log.debug("closeIssueList");
		List<Issue> closedIssues = issuesRepository.findByMilestoneAndState(milestone, IssueState.CLOSED);

		
		model.addAttribute("Issues", closedIssues);
		return closedIssues;
	}
	
}
