package br.com.escolares.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.escolares.domain.Endereco;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface EnderecoRepository extends CrudRepository<Endereco, Integer> {

}
