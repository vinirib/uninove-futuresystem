package br.com.escolares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escolares.domain.Usuario;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsername(String username);

	List<Usuario> findByUsernameNotIn(String username);
}
