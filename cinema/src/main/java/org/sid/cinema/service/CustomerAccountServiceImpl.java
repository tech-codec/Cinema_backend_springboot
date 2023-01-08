package org.sid.cinema.service;

import org.sid.cinema.dao.AppUserRepository;
import org.sid.cinema.dao.SecureTokenRepository;
import org.sid.cinema.entities.AppUser;
import org.sid.cinema.entities.SecureToken;
import org.sid.cinema.exception.InvalidTokenException;
import org.sid.cinema.exception.UnkownIdentifierException;
import org.sid.cinema.security.ForgotPasswordEmailContext;
import org.sid.cinema.web.UpdatePasswordF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import java.util.Objects;

@Service
@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AccountService userService;

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    SecureTokenRepository secureTokenRepository;

    @Value("${site.base.url.https}")
    private String baseURL;

    @Autowired
    private EmailService emailService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void forgottenPassword(String userName) throws UnkownIdentifierException {
        if(!userName.contains("@"))
            throw new RuntimeException("you email is incorrect");
        if(userName == null)
            throw new RuntimeException("vous avez laisse un champ vide");
        if(userName == "")
            throw new RuntimeException("vous avez laisse un champ vide");
        AppUser user= userService.getUserById(userName);
        sendResetPasswordEmail(user);
    }




   @Override
    public void updatePassword(String password, String token) throws InvalidTokenException, UnkownIdentifierException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        AppUser user = userRepository.getOne(secureToken.getUser().getId());
        if(Objects.isNull(user)){
            throw new UnkownIdentifierException("unable to find user for the token");
        }
        secureTokenService.removeToken(secureToken);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }




    protected void sendResetPasswordEmail(AppUser user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void encodePassword(String password, AppUser target){
        target.setPassword(bCryptPasswordEncoder.encode(password));
    }

    @Override
    public AppUser changePassword(Long id, UpdatePasswordF userForm) {
        AppUser user = userRepository.findById(id).orElse(null);
        if(!bCryptPasswordEncoder.matches(userForm.getPassword(),user.getPassword())){
            throw new RuntimeException("votre mot de passe est incorrecte");
        }else {
            if(!userForm.getNewpassword().equals(userForm.getConfirmpassword())){
                throw new RuntimeException("vous avez mal confirmé votre mot de passe");
            }

        }
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getNewpassword()));
        userRepository.save(user);
        return user;
    }
    /*@Override
    public boolean loginDisabled(String username) {
        AppUser user = userRepository.findByEmail(username);
        return user!=null ? user.isLoginDisabled() : false;
    }*/

}
