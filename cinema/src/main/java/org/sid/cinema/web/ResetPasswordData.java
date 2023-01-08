package org.sid.cinema.web;

import lombok.Data;

@Data
public class ResetPasswordData {

    private String email;
    private String token;
    private String password;
    private String repeatPassword;

}
