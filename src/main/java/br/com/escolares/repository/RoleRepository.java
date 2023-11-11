package br.com.escolares.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Role;
import br.com.escolares.domain.RoleType;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	@Query("select r from Role r where r.name = :roleName")
	Role findByName(@Param("roleName")RoleType roleName);
}
