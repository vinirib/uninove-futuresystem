package br.com.escolares.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
public class Professor {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="Digite um nome")
	private String nome;
	@Enumerated(EnumType.STRING)
	@NotNull(message="Escolha uma matéria")
	private Materia materia;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idEndereco", referencedColumnName="id")
	@NotNull(message="Digite o endereço")
	@Valid
	private Endereco endereco;
	@NotBlank(message="Digite o telefone de contato")
	private String telefone;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="professor")
	@OrderBy("data, horaInicio")
	private List<Agenda> agendas;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@Valid
	private Usuario usuario;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Agenda> getAgendas() {
		return agendas;
	}
	
	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
}
