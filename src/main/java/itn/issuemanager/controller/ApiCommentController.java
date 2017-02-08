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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.IssuesRepository;
import itn.issuemanager.repository.LabelRepository;
import itn.issuemanager.repository.MilestoneRepository;


@RestController
@RequestMapping("/api/issue/{issueId}/comment")
public class ApiCommentController {
	
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	private final String USER_SESSION_KEY = "sessionedUser";

	@Autowired
	private IssuesRepository issuesRepository;
	@Autowired
	private CommentRepository commentRepository;
	

	@PostMapping("/create")
	@ResponseBody
	public Comment create(@PathVariable long issueId, Comment comment, HttpSession session) {
		Comment newComment = new Comment(comment, (User) session.getAttribute(USER_SESSION_KEY), issuesRepository.findOne(issueId));
		return commentRepository.save(newComment);
	}

//	@GetMapping("/{id}/edit")
//	public Comment edit(@RequestParam long issueId, @RequestParam long commentId) {
//		Comment resultComment = commentRepository.findOne(commentId);
//		return resultComment;
//	}

	@PutMapping("/{id}")
	public Comment update(@PathVariable long issueId, Comment comment, HttpSession session) {
		Comment modifyComment = commentRepository.findOne(comment.getId());
		log.debug("getid = "+comment.getId());
		modifyComment.update(comment);
		return commentRepository.save(modifyComment);
	}

	@DeleteMapping("/{id}")
	public Comment delete(@PathVariable long issueId, @PathVariable long id) {
		Comment comment = commentRepository.findOne(id);
		commentRepository.delete(comment);
		return comment;
	}
}
