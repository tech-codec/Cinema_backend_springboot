package org.sid.cinema.service;

import org.sid.cinema.entities.AppUser;
import org.sid.cinema.exception.InvalidTokenException;
import org.sid.cinema.exception.UnkownIdentifierException;
import org.sid.cinema.web.UpdatePasswordF;

public interface CustomerAccountService {

    void forgottenPassword(final String userName) throws UnkownIdentifierException;
    void updatePassword(final String password, final String token) throws InvalidTokenException, UnkownIdentifierException;
    void encodePassword(String password, AppUser target);
    //boolean loginDisabled(final String username);
    AppUser changePassword(Long id, UpdatePasswordF userForm);
}
