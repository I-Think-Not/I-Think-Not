package itn.issuemanager.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;

@RestController
@RequestMapping("/api/issue")
public class ApiIssueController {

	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);
	
	@Autowired
	private IssuesRepository issuesRepository;

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
	
	@PutMapping("/{id}")
	public String updateAssignee(@PathVariable Long id, User assignee, @LoginUser User user) throws Exception {
		if(user.isSameUser(user)){
			Issue modifyIssue = issuesRepository.findOne(id);
			modifyIssue.addAssignee(assignee);
			issuesRepository.save(modifyIssue);
		}
		return "redirect:/";
	}
	
}
