package loananhshop.api.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import loananhshop.api.dto.JwtAuthResponse;
import loananhshop.api.dto.LoginDto;
import loananhshop.api.dto.RegisterDto;
import loananhshop.api.exception.AppAPIException;
import loananhshop.api.model.Role;
import loananhshop.api.model.User;
import loananhshop.api.repository.RoleRepository;
import loananhshop.api.repository.UserRepository;
import loananhshop.api.security.JwtTokenProvider;
import loananhshop.api.service.AuthService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        // check username is already exists in database
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        // check email is already exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();

        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(Role.ROLE_USER);

        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully!.";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
                loginDto.getUsernameOrEmail());

        String role = null;
        if (userOptional.isPresent()) {
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if (optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse loginOrCreateBySNS(LoginDto loginDto) {
        String DEFAULT_PASSWORD_FOR_SNS_USER = "defaultPasswordForSNSUser";
        User existUser = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail()).orElse(null);
        if (ObjectUtils.isEmpty(existUser)) {
            User userSNS = new User();

            userSNS.setName(loginDto.getName());
            userSNS.setUsername(loginDto.getUsernameOrEmail());
            userSNS.setEmail(loginDto.getUsernameOrEmail());
            userSNS.setPhotoUrl(loginDto.getPhotoUrl());
            userSNS.setProvider(loginDto.getProvider());
            userSNS.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD_FOR_SNS_USER));

            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName("ROLE_USER");
            roles.add(role);
            userSNS.setRoles(roles);
            userRepository.save(userSNS);
        }
        loginDto.setPassword(DEFAULT_PASSWORD_FOR_SNS_USER);
        return login(loginDto);
    }
}
