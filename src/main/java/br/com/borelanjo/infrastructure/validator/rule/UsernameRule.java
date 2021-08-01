package br.com.borelanjo.infrastructure.validator.rule;

import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;

import java.util.regex.Pattern;

public class UsernameRule implements Rule {
    private static final String regex = "^[a-zA-Z0-9._-]{3,}$";
    private static final Pattern pattern = Pattern.compile(regex);

    private final String message = "'username' precisa seguir essa regra: 'a-z', 'A-Z', '0-9', '.', '-' e '_'.";

    private final String value;

    public UsernameRule(String value) {
        this.value = value;
    }

    @Override
    public void validate() {
        if (!pattern.matcher(value).matches()) {
            throw new FieldInvalidException(message);
        }

    }
}
