package org.sid.cinema.service;

import org.sid.cinema.entities.AppRole;
import org.sid.cinema.entities.AppUser;
import org.sid.cinema.exception.InvalidTokenException;
import org.sid.cinema.exception.UnkownIdentifierException;
import org.sid.cinema.exception.UserAlreadyExistException;
import org.sid.cinema.web.UserData;


public interface AccountService {
    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);
    public void addRoleToUse(String username, String roleName);
    public AppUser findUserByEmail(String email);
    public AppUser findUserByUsername(String username);

    public AppUser register(final UserData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final AppUser user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    AppUser getUserById(final String id) throws UnkownIdentifierException;
}
