package br.com.escolares.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
public class Responsavel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message="Digite o nome")
	private String nome;
	@NotBlank(message="Digite o cpf")
	@Column(unique=true)
	private String cpf;
	@OneToMany(mappedBy="responsavel", cascade=CascadeType.ALL)
	private List<Aluno> filhos;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="responsavel")
	private List<Notificacao> notificacao;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="endereco_id")
	@NotNull(message="Entre com o endereço")
	@Valid
	private Endereco endereco;
	@Email(message="Digite um email válido")
	@NotBlank(message="Digite o email do responsável")
	private String emailContato;
	@NotBlank(message="Digite o telefone de contato")
	private String telefone;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@Valid
	private Usuario usuario;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public List<Aluno> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Aluno> filhos) {
		this.filhos = filhos;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public List<Notificacao> getNotificacao() {
		return notificacao;
	}
	
	public void setNotificacao(List<Notificacao> notificacao) {
		this.notificacao = notificacao;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Responsavel other = (Responsavel) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
