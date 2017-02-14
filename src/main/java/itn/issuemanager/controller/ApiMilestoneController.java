package itn.issuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.MilestoneRepository;

@RestController
@RequestMapping("/api/issue/{issueId}")
public class ApiMilestoneController {
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private IssuesRepository issuesRepository; 
	@Autowired
	private MilestoneRepository milestoneRepository;
	
	@PostMapping("/setMilestone/{milestoneId}") ///{issueId}/setMilestone/{milestoneId}
	public Milestone setMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId, @LoginUser User user) throws Exception{
		if(!user.isSameUser(user)){
			throw new Exception("you can't add Milestone");
		}
		Issue issue=issuesRepository.findOne(issueId);
		Milestone milestone=milestoneRepository.findOne(milestoneId);
		issue.setMilestone(milestone);
		issuesRepository.save(issue);
		log.debug("ajax setMilestone");
		
		return milestone;
	}
}
