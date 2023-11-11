package br.com.escolares.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
@Table(
    name="notificacao", 
    uniqueConstraints=
        @UniqueConstraint(columnNames={"aluno_id", "materia", "responsavel_id"})
    )
public class Notificacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "responsavel_id")
	@NotNull(message="Digite o responsável")
	private Responsavel responsavel;
	@OneToOne
	@JoinColumn(name = "aluno_id")
	@NotNull(message="Digite o aluno")
	private Aluno aluno;
	@Enumerated(EnumType.STRING)
	@NotNull(message="Digite a matéria")
	private Materia materia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	@Override
	public String toString() {
		return "Notificacao [id=" + id + ", responsavel=" + responsavel.getNome() + ", aluno=" + aluno.getNome() + ", materia=" + materia
				+ "]";
	}
	
	
}
