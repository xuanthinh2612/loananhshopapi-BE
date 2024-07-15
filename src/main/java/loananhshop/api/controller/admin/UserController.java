package loananhshop.api.controller.admin;

import loananhshop.api.dto.UserDto;
import loananhshop.api.model.User;
import loananhshop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class UserController extends AdminBaseController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
//      handler method to handle list of users
        List<UserDto> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/disableUser/{id}")
    public String disableUser(@PathVariable String id) {
        return "redirect:/users";
    }
}
