package itn.issuemanager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.ForbiddenTypeException;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;
import itn.issuemanager.repository.MilestoneRepository;
import itn.issuemanager.repository.UserRepository;

@Controller
@RequestMapping("/issue")
public class IssuesController {

	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);
	
	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private MilestoneRepository milestoneRepository; 
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private UserRepository userRepository;

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
	public String create(String subject, String contents, @LoginUser User user) {
		Issue newIssue = new Issue(subject, contents, user);
		issuesRepository.save(newIssue);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
	    // TODO 정렬 기준을 만들어 데이터를 조회한다.
		List<Milestone> mileStones = milestoneRepository.findAll();
		List<Label> labels = labelRepository.findAll();
		List<User> users= userRepository.findAll();
		Issue showIssue = issuesRepository.findOne(id);
		model.addAttribute("issue", showIssue);
		model.addAttribute("mileStones", mileStones);
		model.addAttribute("labelList", labels);
		model.addAttribute("users", users);
		return "issue/show";
	}

	@GetMapping("/{id}/edit") 
	public String edit(@PathVariable Long id, Model model, @LoginUser User loginUser) throws Exception {
		Issue modifyIssue = issuesRepository.findOne(id);
		if(!loginUser.isSameUser(modifyIssue.getWriter())){
			throw new ForbiddenTypeException();
		}
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
	public String delete(@PathVariable Long id, Model model, HttpSession session, @LoginUser User loginUser) throws Exception {
		Issue issue = issuesRepository.findOne(id);
		
		if(!loginUser.isSameUser(issue.getWriter())){
			throw new ForbiddenTypeException();
		}
		
		if (issue != null) {
			issuesRepository.delete(id);
		}
		return "redirect:/";
	}
	
	@GetMapping("/{issueId}/setMilestone/{milestoneId}")
	public String setMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId) {
		Issue issue = issuesRepository.findOne(issueId);
		issue.setMilestone(milestoneRepository.findOne(milestoneId));
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setLabel/{labelId}")
	public String setLabel(@PathVariable Long issueId, @PathVariable Long labelId) throws Exception{
		Issue issue = issuesRepository.findOne(issueId);
		Label label = labelRepository.findOne(labelId);
		issue.addLabel(label);	
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setClose")
	public String setClose(@PathVariable Long issueId, @LoginUser User user) throws Exception {
		Issue issue = issuesRepository.findOne(issueId);
		log.debug("issue writer:{}", issue.getWriter().toString());
		log.debug("user:{}", user.toString());
		
		if(!user.isSameUser(issue.getWriter()) && !issue.getAssignee().contains(user)){
			throw new ForbiddenTypeException();
		}
		issue.closeIssue();
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setReopen")
	public String setReopen(@PathVariable Long issueId, @LoginUser User user) throws Exception {
		Issue issue = issuesRepository.findOne(issueId);
		if(!user.isSameUser(issue.getWriter()) && !issue.getAssignee().contains(user)){
			throw new ForbiddenTypeException();	 
		}
		issue.reopenIssue();
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setAssignee")
	public String setAssignee(@PathVariable Long issueId, String userId) throws Exception {
		Issue issue = issuesRepository.findOne(issueId);
		log.debug(userId);
		User user = userRepository.findByUserId(userId);
		issue.addAssignee(user);
		issuesRepository.save(issue);
		log.debug(user.toString());
		return "redirect:/issue/"+issueId;
	}
	
}
