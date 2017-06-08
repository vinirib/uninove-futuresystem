package br.com.escolares.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
public class Falta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;
	@OneToOne
	@JoinColumn(name = "aluno_id")
	@NotNull(message = "A falta é para qual aluno?")
	private Aluno aluno;
	@NotNull(message = "Digite a data")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate data;
	@NotNull(message = "Preencha o horário de início da aula")
	private LocalTime horaInicio;
	@NotNull(message = "Preencha o horário de fim da aula")
	private LocalTime horaFim;
	
	private boolean notificado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public LocalTime getHoraFim() {
		return horaFim;
	}
	
	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}

	public boolean getNotificado() {
		return notificado;
	}
	
	public void setNotificado(boolean notificado) {
		this.notificado = notificado;
	}
}
