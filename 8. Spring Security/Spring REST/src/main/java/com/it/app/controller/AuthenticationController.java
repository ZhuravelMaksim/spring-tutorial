package com.it.app.controller;

import com.it.app.dto.UserRegistrationRequestDTO;
import com.it.app.dto.request.AuthenticationRequestDTO;
import com.it.app.dto.response.TokenResponseDTO;
import com.it.app.model.Role;
import com.it.app.model.User;
import com.it.app.service.RoleService;
import com.it.app.service.UserService;
import com.it.app.service.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;

    private final RoleService roleService;

    private final TokenService tokenService;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService, RoleService roleService, TokenService tokenService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/signIn")
    public TokenResponseDTO authenticateUser(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponseDTO(tokenService.generate(authentication));
    }

    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(@RequestParam String token) {
        return new TokenResponseDTO(tokenService.refresh(token));
    }

    @PostMapping("/signUp")
    public User registerUser(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final User user = new User();
        user.setName(userRegistrationRequestDTO.getUsername());
        user.setPassword(encoder.encode(userRegistrationRequestDTO.getPassword()));
        final Set<Role> roles = userRegistrationRequestDTO.getRoles().stream()
                .map(roleService::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return userService.save(user);
    }
}
