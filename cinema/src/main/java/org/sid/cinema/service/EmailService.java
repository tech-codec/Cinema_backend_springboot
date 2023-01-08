package org.sid.cinema.service;

import org.sid.cinema.security.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
