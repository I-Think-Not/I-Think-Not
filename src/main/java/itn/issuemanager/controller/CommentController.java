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
import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.UploadFile;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.FileRepository;
import itn.issuemanager.repository.IssuesRepository;


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
	@Autowired
	private FileRepository fileRepository;
	
	
	@GetMapping("/")
	public String index() {
		return "comment/list";
	}

	@PostMapping("/create")
	public String create(@PathVariable long issueId, Comment comment,  @LoginUser User user, List<Long> fileId) {
		Comment newComment = new Comment(comment, user, issuesRepository.findOne(issueId));
		List<UploadFile> files = fileRepository.findAll(fileId);
		newComment.setFiles(files);
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
	
	@GetMapping("/popUpComment")
	public String show(@PathVariable long issueId, Model model) {
		Issue showIssue = issuesRepository.findOne(issueId);
		List<Comment> comments = commentRepository.findByIssue(showIssue);
		model.addAttribute("comments", comments);
		log.debug("showIssue : {}", showIssue);
		log.debug("comment : {}", comments.toString());
		return "/comment/show";
	}
	
}
