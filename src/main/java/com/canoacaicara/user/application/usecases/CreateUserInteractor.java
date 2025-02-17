package com.canoacaicara.user.application.usecases;

import com.canoacaicara.user.application.mapper.UserDTOMapper;
import com.canoacaicara.user.infrastructure.controllers.CreateUserRequest;
import com.canoacaicara.user.infrastructure.controllers.UserResponse;
import com.canoacaicara.user.infrastructure.gateways.UserGateway;
import com.canoacaicara.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateUserInteractor {
    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public CreateUserInteractor(UserGateway userGateway, PasswordEncoder passwordEncoder, UserDTOMapper userDTOMapper) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
        this.userDTOMapper = userDTOMapper;
    }

    public UserResponse createUser(CreateUserRequest request) throws Exception {
        User userDomain = userDTOMapper.toUser(request);
        String hashedPassword = passwordEncoder.encode(userDomain.password());
        User userWithHashedPassword = userDomain.userWithHashedPassowrd(hashedPassword);

        try {
            User userCreated = userGateway.createUser(userWithHashedPassword);
            return userDTOMapper.toResponse(userCreated);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
