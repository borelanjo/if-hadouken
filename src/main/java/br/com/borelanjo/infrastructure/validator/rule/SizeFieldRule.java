package br.com.borelanjo.infrastructure.validator.rule;

import br.com.borelanjo.infrastructure.validator.exception.FieldInvalidException;

public class SizeFieldRule implements Rule {
    private final String messageTemplate = "'%s' deve ser maior que %s e menor que %s, mas tem %s.";

    private final String field;
    private final String value;
    private final int min;
    private final int max;

    public SizeFieldRule(String field, String value, int min, int max) {
        this.field = field;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public void validate() {
        if (value.length() < min || value.length() > max) {
            throw new FieldInvalidException(String.format(messageTemplate, field, min, max, value.length()));
        }

    }
}
