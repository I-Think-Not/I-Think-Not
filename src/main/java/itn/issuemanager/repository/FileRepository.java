package itn.issuemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.UploadFile;

public interface FileRepository extends JpaRepository<UploadFile, Long>{

}
