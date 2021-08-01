package br.com.borelanjo.infrastructure.validator.rule;

import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;

public class EmptyFieldRule implements Rule {
    private final String messageTemplate = "'%s' não pode ser vazio.";

    private final String field;
    private final String value;

    public EmptyFieldRule(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public void validate() {
        if (value == null || value.isBlank()) {
            throw new FieldInvalidException(String.format(messageTemplate, field));
        }

    }
}
