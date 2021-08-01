package br.com.borelanjo.infrastructure.validator.rule;

import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;

import java.util.regex.Pattern;

public class EmailRule implements Rule {
    private static final String regex = "^(.+)@(.+)$";
    private static final Pattern pattern = Pattern.compile(regex);

    private final String message = "Você precisa fornecer um e-mail valido.";

    private final String value;

    public EmailRule(String value) {
        this.value = value;
    }

    @Override
    public void validate() {
        if (!pattern.matcher(value).matches()) {
            throw new FieldInvalidException(message);
        }

    }
}
