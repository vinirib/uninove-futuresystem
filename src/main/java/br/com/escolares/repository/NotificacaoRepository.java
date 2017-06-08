package br.com.escolares.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Materia;
import br.com.escolares.domain.Notificacao;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer>{

	@Query("Select n from Notificacao n inner join n.responsavel r where r.id = :responsavelId")
	List<Notificacao> findByResponsavel(@Param("responsavelId")Integer responsavelId);

	@Query("Select n from Notificacao n join fetch n.responsavel r join fetch n.aluno a where r.id = :responsavelId and "
			+ "a.id = :alunoId and n.materia = :materia")
	Notificacao findByResponsavelAlunoMateria(@Param("responsavelId")Integer responsavelId, 
			@Param("alunoId")Integer alunoId,@Param("materia") Materia materia);

}
