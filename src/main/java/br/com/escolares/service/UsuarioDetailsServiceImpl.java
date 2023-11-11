package br.com.escolares.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.escolares.domain.Usuario;
import br.com.escolares.repository.UsuarioRepository;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository userRepository;


    public UsuarioDetailsServiceImpl(UsuarioRepository userRepository){
        this.userRepository=userRepository;
    }
	
	@Override
	@Transactional(readOnly = true)
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = new Usuario();
		try {
			user = userRepository.findByUsername(username);
			
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
		return user;
	}
}
