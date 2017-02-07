package itn.issuemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.Milestone;

public interface MilestoneRepository extends JpaRepository<Milestone,Long>{

	
}
