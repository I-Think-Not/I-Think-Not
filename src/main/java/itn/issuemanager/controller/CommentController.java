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


@Controller
@RequestMapping("/issue/{issueId}/comment")
public class CommentController {
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	private final String USER_SESSION_KEY = "sessionedUser";

	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping("/")
	public String index() {
		return "comment/list";
	}

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
