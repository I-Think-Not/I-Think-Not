package itn.issuemanager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssuesRepository;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.LabelRepository;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.MilestoneRepository;
import itn.issuemanager.domain.User;

@Controller
@RequestMapping("/issue")
public class IssuesController {

	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);
	private final String USER_SESSION_KEY = "sessionedUser";

	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private MilestoneRepository milestoneRepository; 
	@Autowired
	private LabelRepository labelRepository;

	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("issues", issuesRepository.findAll());
		return "index";
	}

	@GetMapping("/new")
	public String form(HttpSession session) {
		return "issue/form";
	}

	@PostMapping("/")
	public String create(String subject, String contents, HttpSession session) {
		User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
		Issue newIssue = new Issue(subject, contents, sessionUser);
		issuesRepository.save(newIssue);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		List<Milestone> mileStones = milestoneRepository.findAll();
		List<Label> labels = (List<Label>) labelRepository.findAll();
		model.addAttribute("issue", issuesRepository.findOne(id));
		model.addAttribute("mileStones", mileStones);
		model.addAttribute("labelList", labels);
		return "issue/show";
	}

	@GetMapping("/{id}/edit") 
	public String edit(@PathVariable Long id, Model model) {
		Issue modifyIssue = issuesRepository.findOne(id);
		model.addAttribute("modifyIssue", modifyIssue);
		return "issue/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String subject, String contents) {
		Issue modifyIssue = issuesRepository.findOne(id);
		modifyIssue.update(subject, contents);
		issuesRepository.save(modifyIssue);
		return "redirect:/";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Issue issue = issuesRepository.findOne(id);
		if (!issue.equals(null)) {
			issuesRepository.delete(id);
		}
		return "redirect:/";
	}
	
	@GetMapping("/{issueId}/setMilestone/{milestoneId}")
	public String setMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId) {
		Issue issue = issuesRepository.findOne(issueId);
//		if(issue.getMilestone().getId() == milestoneId){
//			
//		}
		issue.setMilestone(milestoneRepository.findOne(milestoneId));
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setLabel/{labelId}")
	public String setLabel(@PathVariable Long issueId, @PathVariable Long labelId) {
		Issue issue = issuesRepository.findOne(issueId);
		Label label = labelRepository.findOne(labelId);
		/*
		  한번에 여러개의 label을 선택하여 넣는 것이 아닌 한번에 하나씩 넣고 issue의 list<label>에 들어가므로
		 issue의 setLabels 함수를 수정하여 label이 선택될때마다 list에 add해주는 식으로 바꾸어 놓음
		 */
		issue.setLabels(label);	
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
}
