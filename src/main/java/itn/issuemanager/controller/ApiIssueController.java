package itn.issuemanager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhncorp.lucy.security.xss.XssFilter;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;
import itn.issuemanager.repository.MilestoneRepository;
import itn.issuemanager.repository.UserRepository;

@RestController
@RequestMapping("/api/issue")
public class ApiIssueController {

	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);
	
	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private MilestoneRepository milestoneRepository; 
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private UserRepository userRepository;

//	@GetMapping("/{id}")
//	public String show(@PathVariable long id, Model model) {
//	    // TODO 정렬 기준을 만들어 데이터를 조회한다.
//		List<Milestone> mileStones = milestoneRepository.findAll();
//		List<Label> labels = labelRepository.findAll();
//		List<User> users= userRepository.findAll();
//		Issue showIssue = issuesRepository.findOne(id);
//		model.addAttribute("issue", showIssue);
//		model.addAttribute("mileStones", mileStones);
//		model.addAttribute("labelList", labels);
//		model.addAttribute("users", users);
//		return "issue/show";
//	}

	@PostMapping("/{issueId}/setMilestone/{milestoneId}") ///{issueId}/setMilestone/{milestoneId}
	public Milestone setMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId){
		Issue issue=issuesRepository.findOne(issueId);
		Milestone milestone=milestoneRepository.findOne(milestoneId);
		issue.setMilestone(milestone);
		issuesRepository.save(issue);
		log.debug("ajax setMilestone");
		
		return milestone;
	}
	@PutMapping("/{id}")
	public String updateAssignee(@PathVariable Long id, User assignee) throws Exception {
		
		Issue modifyIssue = issuesRepository.findOne(id);
		modifyIssue.addAssignee(assignee);
		issuesRepository.save(modifyIssue);
		return "redirect:/";
	}
	
}
