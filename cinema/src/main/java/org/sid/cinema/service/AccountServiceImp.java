package org.sid.cinema.service;


import org.sid.cinema.dao.AppRoleRepository;
import org.sid.cinema.dao.AppUserRepository;
import org.sid.cinema.dao.SecureTokenRepository;
import org.sid.cinema.entities.AppRole;
import org.sid.cinema.entities.AppUser;
import org.sid.cinema.entities.SecureToken;
import org.sid.cinema.exception.InvalidTokenException;
import org.sid.cinema.exception.UnkownIdentifierException;
import org.sid.cinema.exception.UserAlreadyExistException;
import org.sid.cinema.security.AccountVerificationEmailContext;
import org.sid.cinema.web.UserData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import java.util.Objects;

@Service
@Transactional
public class AccountServiceImp implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppRoleRepository roleRepository;


/******************************************************************/
    @Autowired
    private EmailService emailService;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    SecureTokenRepository secureTokenRepository;


    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    public AppUser saveUser(AppUser user) {
        String hasHPW = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hasHPW);
        return userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {

        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUse(String username, String roleName) {
        AppRole role = roleRepository.findByRoleName(roleName);
        AppUser user= userRepository.findByUsername(username);
        user.getRoles().add(role);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    /*******************************************************************************/



    @Override
    public AppUser register(UserData user) throws UserAlreadyExistException {
        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        if(!user.getPassword().equals(user.getRepassword()))
            throw new RuntimeException("you must confirm your password");
        if(!user.getEmail().contains("@"))
            throw new RuntimeException("you email is incorrect");
        if(user.getEmail() == null|| user.getPassword() == null || user.getRepassword() == null || user.getUsername() == null)
            throw new RuntimeException("vous avez laisse un champ vide");
        if(user.getEmail() == "" || user.getPassword() == "" || user.getRepassword() == "" || user.getUsername() == "")
            throw new RuntimeException("vous avez laisse un champ vide");
        AppUser userEntity = new AppUser();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(user, userEntity);
        userEntity.setAccountVerified(true);
        userRepository.save(userEntity);
        addRoleToUse(user.getUsername(),"USER");
        sendRegistrationConfirmationEmail(userEntity);
        return userEntity;
    }


    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email)!=null ? true : false;
    }

    @Override
    public void sendRegistrationConfirmationEmail(AppUser user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        AppUser user = userRepository.getOne(secureToken.getUser().getId());
        if(Objects.isNull(user)){
            return false;
        }
        user.setAccountVerified(false);
        userRepository.save(user); // let's same user details

        // we don't need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }

    @Override
    public AppUser getUserById(String id) throws UnkownIdentifierException {
        AppUser user= userRepository.findByEmail(id);
        if(user == null || user.isAccountVerified()==true){
            // we will ignore in case account is not verified or account does not exists
            throw new UnkownIdentifierException("unable to find account or account is not active");
        }
        return user;
    }

    private void encodePassword(UserData source, AppUser target){
        target.setPassword(bCryptPasswordEncoder.encode(source.getPassword()));
    }

}
