/**
 * 
 */
package br.com.escolares.util;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author vinicius Ribeiro
 *
 * 31 de mai de 2017
 *
 */
public class GeradorDeDataDeNascimento {

	
	public static LocalDate geraDataEntre(LocalDate inicio, LocalDate fim ){
		return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(inicio.toEpochDay(), fim.toEpochDay()));
	}
}
