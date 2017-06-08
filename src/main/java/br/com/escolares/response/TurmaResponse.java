package br.com.escolares.response;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class TurmaResponse {

	private Integer id;
	private String nome;
	private String qtdAlunos;
	private String turnoSala;
	private String numeroSala;
	private String msg;

	public TurmaResponse(Integer id, String nome, String qtdAlunos, String turnoSala, String numeroSala, String msg) {
		super();
		this.id = id;
		this.nome = nome;
		this.qtdAlunos = qtdAlunos;
		this.turnoSala = turnoSala;
		this.numeroSala = numeroSala;
		this.msg = msg;
	}

	public TurmaResponse(String msg) {
		super();
		this.msg = msg;
	}

	public TurmaResponse() {
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

	public String getQtdAlunos() {
		return qtdAlunos;
	}

	public void setQtdAlunos(String qtdAlunos) {
		this.qtdAlunos = qtdAlunos;
	}

	public String getTurnoSala() {
		return turnoSala;
	}

	public void setTurnoSala(String turnoSala) {
		this.turnoSala = turnoSala;
	}

	public String getNumeroSala() {
		return numeroSala;
	}

	public void setNumeroSala(String numeroSala) {
		this.numeroSala = numeroSala;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
