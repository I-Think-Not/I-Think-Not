package itn.issuemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itn.issuemanager.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
	User findByUserId(String userId);
}
