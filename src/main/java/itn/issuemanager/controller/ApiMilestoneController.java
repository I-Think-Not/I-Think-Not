package itn.issuemanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssueState;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.MilestoneRepository;

@RestController
@RequestMapping("/api/milestone/{id}")
public class ApiMilestoneController {
	private static final Logger log = LoggerFactory.getLogger(MilestonesController.class);
	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private MilestoneRepository milestoneRepository; 
	
	@PostMapping("/openIssues") 
	public List<Issue> openIssueList(@PathVariable Long id, Model model){
		List<Issue> openIssues = stateIssues(id, model, IssueState.OPEN);
		return openIssues;
	}
	
	@PostMapping("/closeIssues")
	public List<Issue> closeIssueList(@PathVariable Long id,Model model){
		List<Issue> closedIssues = stateIssues(id, model, IssueState.CLOSED);
		return closedIssues;
	}

	private List<Issue> stateIssues(Long id, Model model, IssueState state) {
		Milestone milestone = milestoneRepository.findOne(id);
		List<Issue> stateIssue = issuesRepository.findByMilestoneAndState(milestone, state);
		model.addAttribute("Issues", stateIssue);
		return stateIssue;
	}
		
}
