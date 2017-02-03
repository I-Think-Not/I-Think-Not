package itn.issuemanager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
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
}
