package br.com.borelanjo.infrastructure.validator;

import br.com.borelanjo.infrastructure.validator.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    private final List<Rule> rules = new ArrayList<>();

    public Validator addRule(Rule rule) {
        rules.add(rule);
        return this;
    }

    public void process() {
        for (Rule r : rules) {
            r.validate();
        }
    }
}
