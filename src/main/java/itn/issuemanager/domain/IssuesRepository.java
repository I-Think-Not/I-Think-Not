package itn.issuemanager.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuesRepository extends JpaRepository<Issue, Long>{
}
