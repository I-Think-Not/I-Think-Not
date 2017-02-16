package itn.issuemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.Comment;
import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssueState;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByIssue(Issue issue);
}
