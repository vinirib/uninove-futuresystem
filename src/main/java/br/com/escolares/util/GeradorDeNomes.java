/**
 * 
 */
package br.com.escolares.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author vinicius Ribeiro
 *
 * 29 de mai de 2017
 *
 */
public class GeradorDeNomes {

	private static List<String> nomes = Arrays.asList("João", "Valter", "Renato", "Vivian", "Suzane", 
			"Ariel", "Bruno", "Felipe", "Rodrigo", "Amanda",
			"Ricardo","Simone", "Gustavo", "Flavio", "Diego", "Diogo", "Regiane", "Ana",
			"Alessandra","Alexandre", "Gabriela", "Gabriel", "Bianca", "Carlos", "Daniel",
			"Everton","Gilson", "Henrique", "Julio", "Luiz", "Mariana","Catia", "Jaqueline",
			"Aline", "Naiara", "Luan", "Luciene", "Michele", "Camila", "Thiago", "Adriana",
			"Robson", "Wagner", "Juliana", "Tamires", "Sara", "Caique", "Cibele", "Marta",
			"Luciana", "Rosangela", "Anderson", "Beatriz", "Caroline", "Paulo", "Pedro", "Victor",
			"Marcio", "Marcelo", "Sabrina", "Fernando", "Fernanda", "Kelly", "Andrezza", "Roberta",
			"Vitoria", "Nicoli", "Rafael", "Rita", "Patricia", "Cassia", "Edna", "Erica", "Gilmar",
			"Roberto", "Alex", "Alison", "Sergio", "Vanessa", "Cristina", "Cristiane", "Cintia");
	
	private static List<String> sobrenomes = Arrays.asList("Ferreira", "Ribeiro", "Soares", "Alves", "de Souza",
			"Almeida", "Vaccari", "Pacheco","Filho", "Lobo", "Vieira", "Perez", "Xavier", "Rodrigues",
			"Nascimento", "Barbosa", "Cruz", "Dutra", "Dias", "Borges","da Costa", "Lima", "Reis",
			"Santana", "Junior", "Magno", "Taveira", "Brito", "Marques", "Ferraz", "Freitas",
			"Farias", "Carvalho", "Peralta", "Marcondes", "Teodoro", "Torres", "Santos", "Marinho",
			"Mello", "Romera", "Magno", "Andrade", "de Jesus", "Gandolfi", "Jardim", "Cardoso", "Nogueira",
			"Bispo", "Silveira", "Xavier", "Esteves", "Alexandrino", "Guedes", "Aguiar", "Arruda",
			"Guimarães", "Coelho", "Maciel", "Leal", "Amaral", "Amorin", "Lucena", "Martins", "Caetano");
	
	
	public static String getNomeAleatorio(){
		Collections.shuffle(nomes);
		return nomes.get(0);
	}
	
	public static String getSobrenomeAleatorio(){
		Collections.shuffle(sobrenomes);
		return sobrenomes.get(0);
	}
	
	public static String getNomeComSobrenomeAleatorio(){
		return getNomeAleatorio()+" "+getSobrenomeAleatorio()+" "+getSobrenomeAleatorio();
	}

	public static List<String> constroiListaDeNomesComSobrenomes(int quantidade){
		Set<String> nomesComSobrenomes = new HashSet<>(quantidade);
		for (int i = 0; i < quantidade; i++) {
			nomesComSobrenomes.add(getNomeComSobrenomeAleatorio());
		}
		return new ArrayList<String>(nomesComSobrenomes);
	}
	
	public static String geraNomeComGrauDeParentesco(String sobrenome){
		return getNomeAleatorio()+" "+sobrenome; 
	}
	
}
