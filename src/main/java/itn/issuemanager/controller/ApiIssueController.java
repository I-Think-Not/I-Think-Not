package itn.issuemanager.controller;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
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
	private UserRepository userRepository;

	// TODO 사용하지 않는 코드는 바로 제거한다.
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
		Milestone milestone = milestoneRepository.findOne(milestoneId);
		issue.setMilestone(milestone);
		issuesRepository.save(issue);
		
		return milestone;
	}
	
	@DeleteMapping("/{issueId}/delassignee/{assigneeId}")
	@Transactional
	public boolean delLabel(@PathVariable Long issueId, @PathVariable Long assigneeId, @LoginUser User user) throws Exception{
		if(!user.isSameUser(user)){
			throw new Exception("you can't delete Label");
		}			
		Issue issue = issuesRepository.findOne(issueId);
		User assignee = userRepository.findOne(assigneeId);
		return issue.removeAssignee(assignee);
	}
	
	@GetMapping("/{issueId}/setAssignee")
	public User updateAssignee(@PathVariable Long issueId, String userId, @LoginUser User user) throws Exception {
		if(!user.isSameUser(user)){
			throw new Exception("you can't updateAssignee");
		}
		log.debug("userId :{}", userId);
		Issue modifyIssue = issuesRepository.findOne(issueId);
		User assignee = userRepository.findByUserId(userId);
		modifyIssue.addAssignee(assignee);
		issuesRepository.save(modifyIssue);
		log.debug("user :{}", assignee);
		return assignee;
	}

}
