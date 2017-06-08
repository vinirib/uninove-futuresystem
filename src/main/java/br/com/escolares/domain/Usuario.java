package br.com.escolares.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.escolares.annotation.PasswordMatches;
import br.com.escolares.annotation.ValidEmail;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Entity
@Table(name = "usuario")
@PasswordMatches /** Custom validation **/
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message="Digite o nome do usuário")
	@Size(min=6,max=80, message="Nome deve conter entre 6 à 30 caracteres")
	private String name;
	@ValidEmail(message="Digite um email válido")
	@NotBlank(message="Digite o email do usuário")
	@Column(unique=true)
	private String username;
	@NotBlank
	private String password;
	@Transient
	@NotBlank(message="Digite sua senha")
	private String passwordForm;
	@Transient
	@NotBlank(message="Digite novamente a senha")
	private String passwordConfirmForm;
    private String avatarPhoto;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "permissao")
    @NotNull(message="Escolha uma permissao")
    private Role role ;
    @NotNull(message="Usuario necessita de um status")
    private Boolean status;
    
    @NotNull(message="Digite a data de cadastro")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCadastro;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getPasswordForm() {
		return passwordForm;
	}

	public void setPasswordForm(String passwordForm) {
		this.passwordForm = passwordForm;
	}

	public String getPasswordConfirmForm() {
		return passwordConfirmForm;
	}

	public void setPasswordConfirmForm(String passwordConfirmForm) {
		this.passwordConfirmForm = passwordConfirmForm;
	}

	public Role getRole() {
        return role;
    }

    public void setRole(Role roles) {
        this.role = roles;
    }
    
    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getAvatarPhoto() {
		return avatarPhoto;
	}
    public void setAvatarPhoto(String avatarPhoto) {
		this.avatarPhoto = avatarPhoto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(role);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + name + ", login=" + username 
                + ", status=" + status + "]";
    }
    @PreUpdate
    public void setPasswordOnTransientFields(){
    	this.setPasswordForm(this.password);
    	this.setPasswordConfirmForm(this.password);
    }
}
