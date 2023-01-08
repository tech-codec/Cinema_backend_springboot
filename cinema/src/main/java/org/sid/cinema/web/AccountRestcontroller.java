package org.sid.cinema.web;


import org.sid.cinema.entities.AppUser;
import org.sid.cinema.exception.InvalidTokenException;
import org.sid.cinema.exception.UserAlreadyExistException;
import org.sid.cinema.service.AccountService;
import org.sid.cinema.service.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@CrossOrigin("*")
@RestController
public class AccountRestcontroller {

    @Autowired
    private AccountService accountService;

    private static final String REDIRECT_LOGIN= "redirect:/login";

    @Autowired
    private AccountServiceImp userService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterForm userForm){
        if(!userForm.getPassword().equals(userForm.getRepassword()))
            throw new RuntimeException("you must confirm your password");
        AppUser user = accountService.findUserByUsername(userForm.getUsername());
        if(user!=null)throw new RuntimeException("this user already exists");
        AppUser appUser = new AppUser();
        appUser.setUsername(userForm.getUsername());
        appUser.setPassword(userForm.getPassword());
        accountService.saveUser(appUser);
        return appUser;
    }



    @PostMapping("/userRegister")
    public void userRegistration(@RequestBody UserData userData, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(""+userData);
        }
        try {
            System.out.println(userData.getUsername());
            userService.register(userData);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
        }

    }


    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam(required = false) String token){
        if(StringUtils.isEmpty(token)){
            return "<h2>la vérification du compte a échoué retournée sur le site vous enregistré</h2>";
        }
        try {
            userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            return " <h2>vous etes déjas enregistré retourné sur le site pour accéder a votre compte.</h2>";
        }
        return " <h2>vous avez été enregigstré avec sucess retourné sur le site pour accéder a votre compte.</h2>";
    }
}
