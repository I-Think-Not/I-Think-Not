package itn.issuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhncorp.lucy.security.xss.XssFilter;

import itn.issuemanager.domain.Issue;
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

	
	@PutMapping("/{id}")
	public String updateAssignee(@PathVariable Long id, User assignee) throws Exception {
		
		Issue modifyIssue = issuesRepository.findOne(id);
		modifyIssue.addAssignee(assignee);
		issuesRepository.save(modifyIssue);
		return "redirect:/";
	}
	
}
