package itn.issuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;

@RestController
@RequestMapping("/api/issue/{issueId}") 
public class ApiLabelController {
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private IssuesRepository issuesRepository;
	
	@PostMapping("/setLabel/{labelId}")
	public Label create(@PathVariable Long issueId, @PathVariable Long labelId) throws Exception{
		log.debug(""+labelId);
		Label label = labelRepository.findOne(labelId);
	    Issue issue = issuesRepository.findOne(issueId);
	    if(issue.getLabels().contains(label)){
			throw new Exception("already exists label");
		}
	    issue.addLabel(label);
	    issuesRepository.save(issue);
	    
		return label;
	}
}
