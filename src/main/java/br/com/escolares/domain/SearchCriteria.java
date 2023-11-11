package br.com.escolares.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class SearchCriteria {
	@JsonProperty("cpf")
	private String cpf;
	private String rg;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}

}
