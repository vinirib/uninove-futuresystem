/**
 * 
 */
package br.com.escolares.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author vinicius Ribeiro
 *
 * 31 de mai de 2017
 *
 */
public class GeradorDeEndereco {
	
	private static List<String> tipoLogradouro = Arrays.asList("Rua", "Avenida");
	private static List<String> nomesDeRuas = Arrays.asList("Aruja", "Monteiro Lobato", "Brasil", "Machado De Assis", "Coronel Brilhante Ustra",
			"Comandante Plínio Salgado", "Vinícius de Moraes", "Castelo Branco", "Santa Cruz", "da Independência", "Washington Luiz", "Aurora",
			"Rio Branco", "Três rios", "Souza Lima", "das Palmeiras", "Antônio de Barros", "Francisco Matarazzo", "Antártica", "Moisés Kauffman",
			"Casa Verde", "Mandaqui", "Valdemar Martins", "Ibiratinga", "Diogo Bueno", "Copacabana", "Edgar Franco", "japeacaba", "Caetano Texeira",
			"Pascoal Gomes de Lima", "Frederico Esteban Junior", "Sapopemba", "Nove De Julho", "Mooca", "Brás Leme", "Imperador José Bonifácio",
			"Cruzeiro do Sul", "Liberdade", "José Paulino", "Angélica", "Consolação", "São Mateus");
	
	private static String getTipoLogradouro(){
		Collections.shuffle(tipoLogradouro);
		return tipoLogradouro.get(0);
	}
	
	private static String getNomeRua(){
		Collections.shuffle(nomesDeRuas);
		return nomesDeRuas.get(0);
	}
	
	public static int geraNumeroCasa(){
		return new Random().ints(10, (3000 + 1)).limit(1).findFirst().getAsInt();
	}
	
	public static String geraEnderecoComNumero(){
		return getTipoLogradouro()+" "+getNomeRua()+", "+geraNumeroCasa();
	}
	
	public static String geraEnderecoSemNumero(){
		return getTipoLogradouro()+" "+getNomeRua();
	}
}
