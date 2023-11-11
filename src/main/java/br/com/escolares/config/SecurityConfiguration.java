package br.com.escolares.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.escolares.repository.UsuarioRepository;
import br.com.escolares.service.UsuarioDetailsServiceImpl;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired 
    private UsuarioRepository userRepository;
    
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/").hasAnyRole("ADMIN", "PROFESSOR", "RESPONSAVEL")
			.antMatchers("/usuarios/editar/").permitAll()
			.antMatchers("/alunos/listar").permitAll()
			.antMatchers("/alunos/listarPorResponsavel").hasRole("RESPONSAVEL")
			.antMatchers("/professores/listar").permitAll()
			.antMatchers("/responsaveis/listar").hasAnyRole("ADMIN", "PROFESSOR")
			.antMatchers("/turmas/listar").permitAll()
			.antMatchers("/faltas/listar").hasAnyRole("ADMIN", "PROFESSOR")
			.antMatchers("/faltas/inserirFaltaAluno").hasRole("PROFESSOR")
			.antMatchers("/faltas/emailTest").hasRole("ADMIN")
			.antMatchers("/faltas/salvarFaltas").hasRole("PROFESSOR")
			.antMatchers("/faltas/remover").hasRole("PROFESSOR")
			.antMatchers("/faltas/faltasDosFilhos").hasRole("RESPONSAVEL")
			.antMatchers("/agendas/porProfessor").hasRole("PROFESSOR")
			.antMatchers("/agendas/agendaDosFilhos").hasRole("RESPONSAVEL")
			.antMatchers("/agendas/agendaDosProfessores").permitAll()
			.antMatchers("/usuarios/**").hasRole("ADMIN")
			.antMatchers("/alunos/**").hasRole("ADMIN")
			.antMatchers("/agendas/**").hasRole("ADMIN")
			.antMatchers("/professores/**").hasRole("ADMIN")
			.antMatchers("/responsaveis/**").hasRole("ADMIN")
			.antMatchers("/turmas/**").hasRole("ADMIN")
			.antMatchers("/faltas/**").denyAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
        	.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
	}

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(bCryptPasswordEncoder());
    }
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new UsuarioDetailsServiceImpl(userRepository);
	}

}
