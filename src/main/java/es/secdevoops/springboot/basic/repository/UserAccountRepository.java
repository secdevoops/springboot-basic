package es.secdevoops.springboot.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.secdevoops.springboot.basic.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>{
	public UserAccount findByUsername(String username);
}
