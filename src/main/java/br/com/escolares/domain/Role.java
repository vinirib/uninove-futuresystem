package br.com.escolares.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = -9090544336478891767L;
	
	@Id
	@Enumerated(EnumType.STRING)
	@NotNull(message="Escolha uma permissão")
	private RoleType name;
	
	
	public RoleType getName() {
		return name;
	}

	public void setName(RoleType name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name.toString();
	}
	
}
