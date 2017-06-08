package br.com.escolares.response;

import java.io.Serializable;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class ResponsavelResponse implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String cpf;
	private String telefone;
	private String msg;
	
	public ResponsavelResponse(Integer id, String nome, String cpf, String telefone, String msg) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.msg = msg;
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
