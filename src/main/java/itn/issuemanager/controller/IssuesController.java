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

import com.nhncorp.lucy.security.xss.XssFilter;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;
import itn.issuemanager.repository.MilestoneRepository;

@Controller
@RequestMapping("/issue")
public class IssuesController {

	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);
	private final String USER_SESSION_KEY = "sessionedUser";
	XssFilter xssFilter = XssFilter.getInstance("/lucy-xss-superset.xml");
	
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
        String filterContents = xssFilter.doFilter(contents);
		Issue newIssue = new Issue(subject, filterContents, sessionUser);
		issuesRepository.save(newIssue);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable long id, Model model) {
		List<Milestone> mileStones = milestoneRepository.findAll();
		List<Label> labels = (List<Label>) labelRepository.findAll();
		Issue showIssue = issuesRepository.findOne(id);
		model.addAttribute("issue", showIssue);
		model.addAttribute("mileStones", mileStones);
		model.addAttribute("labelList", labels);
		return "issue/show";
	}

	@GetMapping("/{id}/edit") 
	public String edit(@PathVariable Long id, Model model) {
		//TODO
		//글쓴이와 로그인유저 체크
		Issue modifyIssue = issuesRepository.findOne(id);
		model.addAttribute("modifyIssue", modifyIssue);
		return "issue/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String subject, String contents) {
		Issue modifyIssue = issuesRepository.findOne(id);
		String filterContents = xssFilter.doFilter(contents);
		modifyIssue.update(subject, filterContents);
		issuesRepository.save(modifyIssue);
		return "redirect:/";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Issue issue = issuesRepository.findOne(id);
		if (issue != null) {
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
	public String setLabel(@PathVariable Long issueId, @PathVariable Long labelId) throws Exception{
		Issue issue = issuesRepository.findOne(issueId);
		Label label = labelRepository.findOne(labelId);
		// 이부분 손봐야함 addLabel 에 이미 존재하는 Label추가시 어떠한 처리를 해야하는지
		issue.addLabel(label);	
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setClose")
	public String setClose(@PathVariable Long issueId) {
		Issue issue = issuesRepository.findOne(issueId);
		issue.closeIssue();
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setReopen")
	public String setReopen(@PathVariable Long issueId) {
		Issue issue = issuesRepository.findOne(issueId);
		issue.reopenIssue();
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	
}
