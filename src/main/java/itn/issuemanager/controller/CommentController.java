package itn.issuemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.CommentRepository;
import itn.issuemanager.domain.IssuesRepository;


@Controller
@RequestMapping("/issue/{issueId}/comment")
public class CommentController {

	@Autowired
	private IssuesRepository issueRepository;	
	@Autowired
	private CommentRepository commentRepository;
	
	@GetMapping("/")
	public String index() {
		return "comment/list";
	}

	@PostMapping("/create")
	public String create(@PathVariable long issueId, Comment comment) {
		comment.setIssue(issueRepository.findOne(issueId));
		commentRepository.save(comment);
		//dbIssue.setComments(comment);
		return "redirect:/issue/"+issueId;
	}

	@GetMapping("/{id}/edit")
	public String edit() {
		return "";
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
