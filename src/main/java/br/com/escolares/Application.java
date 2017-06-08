package br.com.escolares;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@SpringBootApplication
public class Application {
	
    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		printUserNameTutorial();
	}

	private static void printUserNameTutorial() {
		System.out.println("\n\n\n\tBem Vindo ao sistema Controle de escolares");
		System.out.println("\nNossa aplicação roda na porta 8080");
		System.out.println("\nTenha certeza que não tem nenhum outro servidor rodando nessa porta");
		System.out.println("\nO login principal é:");
		System.out.println("\nusuario: admin@futuresystem.com.br");
		System.out.println("\nsenha: 123");
		
	}
}
