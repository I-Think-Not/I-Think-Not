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

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.Label;
import itn.issuemanager.domain.Milestone;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;
import itn.issuemanager.repository.MilestoneRepository;

@Controller
@RequestMapping("/issue")
public class IssuesController {
    // TODO 사용하지 않는 코드 제거한다.
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
	    // TODO 정렬 기준을 만들어 데이터를 조회한다.
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
		//TODO 글쓴이와 로그인유저 체크
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
		if (issue != null) {
			issuesRepository.delete(id);
		}
		return "redirect:/";
	}
	
	@GetMapping("/{issueId}/setMilestone/{milestoneId}")
	public String setMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId) {
		Issue issue = issuesRepository.findOne(issueId);
		// TODO 사용하지 않는 코드 제거한다.
//		if(issue.getMilestone().getId() == milestoneId){
//			
//		}
		issue.setMilestone(milestoneRepository.findOne(milestoneId));
		issuesRepository.save(issue);
		return "redirect:/issue/"+issueId;
	}
	
	@GetMapping("/{issueId}/setLabel/{labelId}")
	public String setLabel(@PathVariable Long issueId, @PathVariable Long labelId) throws Exception {
		Issue issue = issuesRepository.findOne(issueId);
		Label label = labelRepository.findOne(labelId);
		
		// TODO 다음 if 문절의 구현을 setLabels()로 이동하면 어떻게 될까? 근데 이 부분에 대해 Exception을 throw 해야 하나?
		if(issue.getLabels().contains(label)){
			throw new Exception("already exists label");
		}
		/*
		  한번에 여러개의 label을 선택하여 넣는 것이 아닌 한번에 하나씩 넣고 issue의 list<label>에 들어가므로
		 issue의 setLabels 함수를 수정하여 label이 선택될때마다 list에 add해주는 식으로 바꾸어 놓음
		 */
		// TODO Label 추가 메소드 명을 setLabels() => setLabel 또는 addLabel()로 변경할 것을 고려
		issue.setLabels(label);	
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
