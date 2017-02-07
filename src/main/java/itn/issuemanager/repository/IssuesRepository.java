package itn.issuemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.Issue;

public interface IssuesRepository extends JpaRepository<Issue, Long>{
}
