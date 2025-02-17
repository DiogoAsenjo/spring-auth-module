package com.canoacaicara.user.application.usecases;

import com.canoacaicara.security.jwt.JWTService;
import com.canoacaicara.security.jwt.TokenJWT;
import com.canoacaicara.user.application.exceptions.UserNotFoundException;
import com.canoacaicara.user.domain.User;
import com.canoacaicara.user.infrastructure.gateways.UserGateway;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class LoginUserInteractor {
    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;


    public LoginUserInteractor(UserGateway userGateway, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenJWT login(String email, String password) {
        Optional<User> userTryingToLogIn = userGateway.getUser(email);

        if (userTryingToLogIn.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User userFound = userTryingToLogIn.get();

        if (!passwordEncoder.matches(password, userFound.password())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(email);

        return new TokenJWT(token);
    }
}
