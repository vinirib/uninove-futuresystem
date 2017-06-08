package br.com.escolares.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.UsuarioRepository;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository userRepository;
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(Usuario user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPasswordForm()));
		userRepository.save(user);
	}

	@Override
	public Usuario findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	

	public Usuario getLoggedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByUsername(auth.getName());
	}
}
