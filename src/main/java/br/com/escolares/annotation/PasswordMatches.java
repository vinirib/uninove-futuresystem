package br.com.escolares.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.escolares.validation.PasswordMatchesValidator;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Senhas não coincidem, tente novamente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
