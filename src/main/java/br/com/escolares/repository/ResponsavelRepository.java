package br.com.escolares.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Responsavel;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface ResponsavelRepository extends JpaRepository<Responsavel, Integer>{

	Responsavel findByCpf(String cpf);

	@Query("Select r from Responsavel r inner join r.usuario u where u.id = :id")
	public Responsavel findByUsuario(@Param("id")Integer id);

}
