package es.secdevoops.springboot.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.secdevoops.springboot.basic.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	public Role findByRolename(String rolename);
}
