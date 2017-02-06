package itn.issuemanager.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByIssueId(long issueId);	//userId를 기반으로 db에서 조회가능
}
