package itn.issuemanager.controller;

import java.util.Date;

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

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssuesRepository;

@Controller
@RequestMapping("/issue")
public class IssuesController {
	
	private static final Logger log = LoggerFactory.getLogger(IssuesController.class);

	@Autowired
	private IssuesRepository issuesRepository;
	
	@GetMapping("/")	//이슈 리스트
	public String list(Model model) {
		model.addAttribute("issues", issuesRepository.findAll());
		return "index";
	}
	
	@GetMapping("/new")	//생성 폼
	public String form() {
		return "issue/form";
	}
	
	@PostMapping("/")	//생성
	public String create(String subject, String contents) {
		Issue newIssue = new Issue(subject, contents);
		issuesRepository.save(newIssue);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")	//상세보기
	public String show(@PathVariable long id, Model model) {
		Issue dbIssue = issuesRepository.findOne(id);
		model.addAttribute("issue", dbIssue);
		log.debug("1111");
		return "issue/show";
	}
	
	@GetMapping("/{id}/edit")	//수정 폼
	public String edit(@PathVariable Long id, Model model) {
		Issue modifyIssue = issuesRepository.findOne(id);
		model.addAttribute("modifyIssue", modifyIssue);
		return "issue/updateForm";
	}
	
	@PutMapping("/{id}")	//수정하기
	public String update(@PathVariable Long id, Issue inputIssue) {
		inputIssue.setId(id);
		inputIssue.setCreationDate(new Date());
		issuesRepository.save(inputIssue);
		return "redirect:/";
	}
	
	@DeleteMapping("/{id}")	//삭제
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Issue issue = issuesRepository.findOne(id);
		if(!issue.equals(null)){
			issuesRepository.delete(id);
		}
		return "redirect:/";
	}
}
