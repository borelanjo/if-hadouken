package br.com.borelanjo.infrastructure.validator.rule;

import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;
import br.com.borelanjo.presentation.dto.UserTo;

import java.util.regex.Pattern;

public class PasswordRule implements Rule {

    private final String message = "As senhas precisam ser iguais.";

    private final UserTo userTo;

    public PasswordRule(UserTo userTo) {
        this.userTo = userTo;
    }

    @Override
    public void validate() {
        if (!userTo.getPassword().equals(userTo.getPasswordRepeat())) {
            throw new FieldInvalidException(message);
        }

    }
}
