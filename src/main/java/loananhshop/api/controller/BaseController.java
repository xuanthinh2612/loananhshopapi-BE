package loananhshop.api.controller;

import loananhshop.api.dto.UserDto;
import loananhshop.api.model.User;
import loananhshop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {

    @Autowired
    private UserService userService;


    protected UserDto getCurrentLoggedInUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            userDto = userService.findUserDtoByEmail(currentUserEmail);
        }
        return userDto;
    }
    protected User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            user = userService.findByUsernameOrEmail(currentUserEmail);
        }
        return user;
    }
}
