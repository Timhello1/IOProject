package com.example.ioproject.user;

public class Password {
    private final String password;

    public Password(String password) {
        this.password = password;
    }

    public String validatePassword() {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = !password.matches("[A-Za-z\\d]*");

        if (password.length() < 8) {
            return "słabe";
        } else if (hasUppercase && hasLowercase && hasNumber && hasSpecialChar) {
            return "silne";
        } else if ( hasUppercase && hasLowercase && hasNumber) {
            return "średnie";
        } else {
            return "słabe";
        }
    }

    public String getPassword() {
        return password;
    }

}
