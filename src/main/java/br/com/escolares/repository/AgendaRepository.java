package br.com.escolares.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Agenda;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface AgendaRepository extends JpaRepository<Agenda, Integer>{

	@Query("select ag from Agenda ag inner join ag.turma.alunos a where a.turma.id = ag.turma.id and a.id = :alunoId")
	List<Agenda> findAulasFromAluno(@Param("alunoId")Integer alunoId);

	@Query("select ag from Agenda ag inner join ag.professor p where p.id = :id order by ag.data")
	List<Agenda> findByProfessorId(@Param("id")Integer id);

	@Query("select ag from Agenda ag inner join ag.turma.alunos al where al.id in (:alunosIds) order by ag.data")
	List<Agenda> findByAlunos(@Param("alunosIds")List<Integer> alunosIds);

	@Query("select ag from Agenda ag where ag.data BETWEEN :dataInicio AND :dataFim ")
	List<Agenda> findWhereDataBetween(@Param("dataInicio")LocalDate inicio,@Param("dataFim") LocalDate dataFim);
}
