package br.com.escolares.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.escolares.annotation.PasswordMatches;
import br.com.escolares.domain.Usuario;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final Usuario user = (Usuario) obj;
        return user.getPasswordConfirmForm().equals(user.getPasswordForm());
    }

}