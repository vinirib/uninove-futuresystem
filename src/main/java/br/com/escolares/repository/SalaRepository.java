package br.com.escolares.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escolares.domain.Sala;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface SalaRepository  extends JpaRepository<Sala, Integer>{

}
