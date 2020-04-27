package es.secdevoops.springboot.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.secdevoops.springboot.basic.model.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{
	public UserAccount findByUsername(String username);
}
