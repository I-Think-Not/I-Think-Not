package itn.issuemanager.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.ForbiddenTypeException;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;

@RestController
@RequestMapping("/api/issue/{issueId}") 
public class ApiLabelController {
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private IssuesRepository issuesRepository;
	
	@PostMapping("/setLabel/{labelId}")
	public Label addLabel(@PathVariable Long issueId, @PathVariable Long labelId, @LoginUser User user) throws Exception{
		Issue issue = issuesRepository.findOne(issueId);
		if(!issue.getWriter().isSameUser(user)){
			throw new ForbiddenTypeException();
		}	
		Label label = labelRepository.findOne(labelId);
	    issue.addLabel(label);
	    issuesRepository.save(issue);
		return label;
	}
	
	@DeleteMapping("/label/{labelId}")
	@Transactional
	public boolean delLabel(@PathVariable Long issueId, @PathVariable Long labelId, @LoginUser User user) throws Exception{
		Issue issue = issuesRepository.findOne(issueId);
		if(!issue.getWriter().isSameUser(user)){
			throw new ForbiddenTypeException();
		}		
		Label label = labelRepository.findOne(labelId);
		return issue.removeLabel(label);
	}
	
}
