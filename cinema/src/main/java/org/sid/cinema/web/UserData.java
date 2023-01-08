package org.sid.cinema.web;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UserData implements Serializable {

    @NotEmpty(message = "{registration.validation.firstName}")
    private String username;

    /*@NotEmpty(message = "{registration.validation.lastName}")
    private String lastName;*/

    @Email(message = "{registration.validation.email}")
    private String email;

    @NotEmpty(message = "{registration.validation.password}")
    private String password;

    private String repassword;


}
