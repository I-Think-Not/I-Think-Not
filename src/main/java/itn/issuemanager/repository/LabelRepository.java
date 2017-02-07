package itn.issuemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itn.issuemanager.domain.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>{

}
