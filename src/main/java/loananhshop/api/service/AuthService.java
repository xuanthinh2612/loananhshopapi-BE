package loananhshop.api.service;

import loananhshop.api.dto.JwtAuthResponse;
import loananhshop.api.dto.LoginDto;
import loananhshop.api.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
    JwtAuthResponse loginOrCreateBySNS(LoginDto loginDto);

}
