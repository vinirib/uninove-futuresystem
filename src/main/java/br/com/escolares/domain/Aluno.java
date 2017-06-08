package br.com.escolares.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
public class Aluno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "Digite o nome")
	private String nome;
	@NotBlank(message = "Digite o telefone")
	private String telefone;
	@NotBlank(message = "Preencha o RG")
	@Column(unique=true)
	private String rg;
	@ManyToOne
	@JoinColumn(name = "responsavel_id")
	private Responsavel responsavel;
	@ManyToOne
	@JoinColumn(name = "turma_id")
	private Turma turma;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idEndereco", referencedColumnName = "id")
	@NotNull(message = "Digite o endereço")
	@Valid
	private Endereco endereco;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Digite a data de nascimento")
	private LocalDate dataNascimento;
	@OneToMany(mappedBy = "aluno")
	private List<Falta> faltas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public List<Falta> getFaltas() {
		return faltas;
	}
	
	public void setFaltas(List<Falta> faltas) {
		this.faltas = faltas;
	}
}
