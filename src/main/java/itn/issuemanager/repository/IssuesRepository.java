package itn.issuemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.Issue;
import itn.issuemanager.domain.IssueState;
import itn.issuemanager.domain.Milestone;

public interface IssuesRepository extends JpaRepository<Issue, Long>{
	List<Issue> findByMilestoneAndState(Milestone milestone, IssueState state);
	List<Issue> findByStateOrderByCreationDateDesc(IssueState state);
}
