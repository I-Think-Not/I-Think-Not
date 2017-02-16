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
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;

@RestController
@RequestMapping("/api/issue/{issueId}") 
public class ApiLabelController {
    // TODO 사용하지 않는 코드 제거한다.
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private IssuesRepository issuesRepository;
	
	@PostMapping("/setLabel/{labelId}")
	public Label addLabel(@PathVariable Long issueId, @PathVariable Long labelId, @LoginUser User user) throws Exception{
		if(!user.isSameUser(user)){
			throw new Exception("you can't add Label");			//에러 메세지 수정
		}
		Label label = labelRepository.findOne(labelId);
	    Issue issue = issuesRepository.findOne(issueId);
	    
	    // TODO issue.contains(label)와 같이 구현하도록 리팩토링
	    if(issue.getLabels().contains(label)){
			throw new Exception("already exists label");
		}
	    issue.addLabel(label);
	    issuesRepository.save(issue);
		return label;
	}
	
	@DeleteMapping("/label/{labelId}")
	@Transactional
	public boolean delLabel(@PathVariable Long issueId, @PathVariable Long labelId, @LoginUser User user) throws Exception{
		if(!user.isSameUser(user)){
			throw new Exception("you can't delete Label");
		}			
		Issue issue = issuesRepository.findOne(issueId);
		Label label = labelRepository.findOne(labelId);
		return issue.removeLabel(label);
	}
	
}
