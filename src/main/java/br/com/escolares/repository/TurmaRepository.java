package br.com.escolares.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Turma;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface TurmaRepository extends JpaRepository<Turma, Integer>{

	@Query("select t from Turma t join fetch t.sala s where s.numero = :numeroSala")
	Turma findBySalaNumero(@Param("numeroSala")String numeroSala);
}
