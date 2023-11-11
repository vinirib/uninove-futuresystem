package br.com.escolares.domain;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public enum RoleType {

	ROLE_ADMIN("Admin"),
	ROLE_PROFESSOR("Professor"),
	ROLE_RESPONSAVEL("Responsável");
	
	private final String name; 
	
    private RoleType(String s) {
        name = s;
    }
    
    public String getName() {
		return name;
	}

}
