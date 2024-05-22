package loananhshop.api.service;

import loananhshop.api.model.User;
import loananhshop.api.dto.UserDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
    User findUserByPhoneNumber(String phoneNumber);
    UserDto findUserDtoByEmail(String email);

    List<UserDto> findAllUsers();

}
