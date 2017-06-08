package br.com.escolares.domain;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public enum Materia {

	PORTUGUES("Português"),
	MATEMATICA("Matemática"),
	BIOLOGIA("Biologia"),
	GEOGRAFIA("Geografia"),
	EDUCACAO_FISICA("Educação Fisica"),
	HISTORIA("História"),
	INGLES("Inglês"),
	FISICA("Fisica"),
	QUIMICA("Quimica"),
	CIENCIAS("Ciências"),
	ARTES("Artes"),
	INFORMATICA("Informática");
	
	private Materia(final String materia) {
		this.materia = materia;
	}
	private final String materia;
	
	@Override
	public String toString() {
		return materia;
	}
}
