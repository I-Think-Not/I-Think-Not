package itn.issuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.ForbiddenTypeException;
import itn.issuemanager.domain.UploadFile;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.CommentRepository;
import itn.issuemanager.repository.FileRepository;
import itn.issuemanager.repository.IssuesRepository;

@RestController
@RequestMapping("/api/issue/{issueId}/comment")
public class ApiCommentController {
    private static final Logger log = LoggerFactory.getLogger(ApiCommentController.class);

    @Autowired
    private IssuesRepository issuesRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/create")
    public Comment create(@PathVariable long issueId, Comment comment, @LoginUser User user, Long[] fileid) {
        log.debug("issue id : {}", issueId);
        Comment newComment = new Comment(comment, user, issuesRepository.findOne(issueId));
        log.debug("comment : {}", newComment);
        if (fileid != null) {
            for (Long id : fileid) {
                log.debug(id.toString());
                UploadFile file = fileRepository.findOne(id);
                file.uploadComplete();
                newComment.addFile(file);
                fileRepository.save(file);
            }
        }
        return commentRepository.save(newComment);
    }

	@PutMapping("/{id}")
	public Comment update(@PathVariable long issueId, Comment comment, @LoginUser User user) throws Exception {
		Comment modifyComment = commentRepository.findOne(comment.getId());
		log.debug("user = {}", user.toString());
		log.debug("comment = {}", comment.toString());
		
		if(!user.isSameUser(user)){
			throw new ForbiddenTypeException();
		}
		modifyComment.update(comment);
		return commentRepository.save(modifyComment);
	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable long issueId, @PathVariable long id, @LoginUser User user) {
		Comment comment = commentRepository.findOne(id);
		log.debug("commentId : {}", id);
		log.debug("user : {}", user);
		log.debug("comment : {}", comment);
		if (comment.isSameWriter(user)) {
			commentRepository.delete(comment);
			return true;
		}else{
			return false;
		}
	}
	
	@GetMapping("/{id}/userCheck")
	public boolean modifyUserCheck(@PathVariable long issueId, @PathVariable long id, @LoginUser User user) {
		log.debug("modiuser : {}", user.toString());
		return commentRepository.findOne(id).isSameWriter(user);
	}
	
	@GetMapping("/{id}")
	public Comment selectComment(@PathVariable long issueId, @PathVariable long id){
		return commentRepository.findOne(id);
	}
	
}
