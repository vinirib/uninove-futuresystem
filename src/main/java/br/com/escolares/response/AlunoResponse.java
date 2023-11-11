package br.com.escolares.response;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class AlunoResponse {

	private Integer id;
	private String nome;
	private String rg;
	private String dataNascimento;
	private String nomeResponsavel;
	private String telefone;
	private String msg;

	public AlunoResponse(Integer id, String nome, String rg, 
			String dataNascimento, String nomeResponsavel, String telefone, String msg) {
		this.id = id;
		this.nome = nome;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
		this.nomeResponsavel = nomeResponsavel;
		this.telefone = telefone;
		this.msg = msg;
	}

	public AlunoResponse(String msg) {
		super();
		this.msg = msg;
	}

	public AlunoResponse() {
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
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
