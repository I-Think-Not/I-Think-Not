package itn.issuemanager.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.ForbiddenTypeException;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
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

	@PostMapping("/{issueId}/setMilestone/{milestoneId}") 
	public Milestone setMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId, @LoginUser User user) throws ForbiddenTypeException{
		Issue issue=issuesRepository.findOne(issueId);
		Milestone milestone = milestoneRepository.findOne(milestoneId);
		if(!issue.getWriter().isSameUser(user)){
			throw new ForbiddenTypeException();
		}	
		issue.setMilestone(milestone);
		issuesRepository.save(issue);
		
		return milestone;
	}
	
	@DeleteMapping("/{issueId}/delassignee/{assigneeId}")
	@Transactional
	public boolean delAssignee(@PathVariable Long issueId, @PathVariable Long assigneeId, @LoginUser User user) throws Exception{
		Issue issue = issuesRepository.findOne(issueId);
		if(!issue.getWriter().isSameUser(user)){
			throw new ForbiddenTypeException();
		}			
		User assignee = userRepository.findOne(assigneeId);
		return issue.removeAssignee(assignee);
	}
	
	@GetMapping("/{issueId}/setAssignee")
	public User updateAssignee(@PathVariable Long issueId, String userId, @LoginUser User user) throws Exception {
		log.debug("userId :{}", userId);
		Issue modifyIssue = issuesRepository.findOne(issueId);
		if(!modifyIssue.getWriter().isSameUser(user)){
			throw new ForbiddenTypeException();
		}
		User assignee = userRepository.findByUserId(userId);
		modifyIssue.addAssignee(assignee);
		issuesRepository.save(modifyIssue);
		log.debug("user :{}", assignee);
		return assignee;
	}

}
