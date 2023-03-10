package org.sid.cinema.security;

import org.sid.cinema.entities.AppUser;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {
    private String token;


    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        AppUser customer = (AppUser) context; // we pass the customer informati
        put("firstName", customer.getUsername());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("vitechprovitech@gmail.com");
        setTo(customer.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
