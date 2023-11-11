package br.com.escolares.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
public class Turma {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToMany(mappedBy="turma")
	private List<Aluno> alunos = new ArrayList<>();
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="salaId")
	private Sala sala;
	@OneToMany(mappedBy="turma", cascade=CascadeType.ALL)
	@OrderBy("data, horaInicio")
	private List<Agenda> agendaDeAulas;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	public List<Agenda> getAgendaDeAulas() {
		return agendaDeAulas;
	}
	
	public void setAgendaDeAulas(List<Agenda> agendaDeAulas) {
		this.agendaDeAulas = agendaDeAulas;
	}
}
