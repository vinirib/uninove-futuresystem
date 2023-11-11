package br.com.escolares.service;

import br.com.escolares.domain.Usuario;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public interface UsuarioService {

	void save(Usuario user);

    Usuario findByUsername(String username);
}
