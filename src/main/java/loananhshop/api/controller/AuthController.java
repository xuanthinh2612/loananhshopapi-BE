package loananhshop.api.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import loananhshop.api.dto.JwtAuthResponse;
import loananhshop.api.dto.LoginDto;
import loananhshop.api.dto.RegisterDto;
import loananhshop.api.service.AuthService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    // Get the SLF4J logger interface, default Logback, a SLF4J implementation
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    // Build Register REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        try {
            String response = authService.register(registerDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Build Login REST API

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        try {
            JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
            return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new JwtAuthResponse(), HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/loginBySNS")
    public ResponseEntity<JwtAuthResponse> loginBySNS(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.loginOrCreateBySNS(loginDto);
        logger.info("Login By SNS.");
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
