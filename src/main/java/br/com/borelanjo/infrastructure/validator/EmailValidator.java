package br.com.borelanjo.infrastructure.validator;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String regex = "^(.+)@(.+)$";
    private static final Pattern pattern = Pattern.compile(regex);

    public static boolean isValid(String email) {
        return pattern.matcher(email).matches();
    }
}
