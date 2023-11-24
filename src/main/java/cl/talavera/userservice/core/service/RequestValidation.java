package cl.talavera.userservice.core.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RequestValidation {

    private static final String EMAIL_PATTERN = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$";


    private String passwordPattern;
    public  boolean validMail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public  boolean validPassword(String password) {
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    public void setPasswordPattern(String passwordPattern) {
        this.passwordPattern = passwordPattern;
    }

}
