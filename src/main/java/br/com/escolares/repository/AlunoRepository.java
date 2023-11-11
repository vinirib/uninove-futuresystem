package br.com.escolares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Aluno;
import br.com.escolares.domain.Turma;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

	List<Aluno> findAlunoByTurma(@Param("turma")Turma turma);

	Aluno findByRg(@Param("rg")String rg);

	@Query("Select a from Aluno a inner join a.responsavel r where r.id = :responsavelId order by a.nome")
	List<Aluno> findByResponsavel(@Param("responsavelId")Integer responsavelId);
}
