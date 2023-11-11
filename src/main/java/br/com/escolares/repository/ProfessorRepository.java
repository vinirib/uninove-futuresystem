package br.com.escolares.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.escolares.domain.Materia;
import br.com.escolares.domain.Professor;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

	public Professor findByMateria(Materia materia);

	@Query("Select p from Professor p inner join p.usuario u where u.id = :id")
	public Professor findByUsuario(@Param("id")Integer id);

}
