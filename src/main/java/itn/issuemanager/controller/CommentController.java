package itn.issuemanager.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;
import itn.issuemanager.repository.MilestoneRepository;


@Controller
@RequestMapping("/issue/{issueId}/comment")
public class CommentController {
	// TODO 사용하지 않는 코드 제거한다.
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	// TODO 중복된 값. 별도의 상수 값을 만들지 않는다.
	private final String USER_SESSION_KEY = "sessionedUser";

	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping("/")
	public String index() {
		return "comment/list";
	}

	// TODO @LoginUser를 사용하도록 통일한다.
	@PostMapping("/create")
	public String create(@PathVariable long issueId, Comment comment, HttpSession session) {
		Comment newComment = new Comment(comment, (User) session.getAttribute(USER_SESSION_KEY), issuesRepository.findOne(issueId)); 
		commentRepository.save(newComment);
		return "redirect:/issue/"+issueId;
	}

	@GetMapping("/{id}/edit")
	public String edit() {
		return "issue/show";
	}

	@PutMapping("/{id}")
	public String update() {
		return "";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable long issueId, @PathVariable long id) {
		Comment comment = commentRepository.findOne(id);
		commentRepository.delete(comment);
		return "redirect:/issue/"+issueId;
	}
}
