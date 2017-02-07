package itn.issuemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
