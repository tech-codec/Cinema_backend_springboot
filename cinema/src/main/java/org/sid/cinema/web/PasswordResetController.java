package org.sid.cinema.web;

import org.sid.cinema.exception.UnkownIdentifierException;
import org.sid.cinema.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin("*")
@RestController
public class PasswordResetController {

    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String MSG = "resetPasswordMsg";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CustomerAccountService customerAccountService;

    @PostMapping("/request")
    public void resetPassword(@RequestBody ResetPasswordData forgotPasswordForm, RedirectAttributes redirAttr) {
        try {
            System.out.println(forgotPasswordForm.getEmail());
            customerAccountService.forgottenPassword(forgotPasswordForm.getEmail());
        } catch (UnkownIdentifierException e) {
            redirAttr.addFlashAttribute(MSG,
                    messageSource.getMessage("user.forgotpwd.msg", null, LocaleContextHolder.getLocale())
            );
        }

    }


    /*@PostMapping("/changeFrontden")
    public void changePassword(final ResetPasswordData data) {
        try {
            customerAccountService.updatePassword(data.getPassword(), data.getToken());
        } catch (InvalidTokenException | UnkownIdentifierException e) {
            // log error statement

        }

    }*/

    @PostMapping("/UpdatePassword/{userId}")
    public void updatePassword(@RequestBody UpdatePasswordF userForm, @PathVariable Long userId){
      customerAccountService.changePassword(userId,userForm);
    }

}
