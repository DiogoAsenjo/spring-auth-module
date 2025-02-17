package com.canoacaicara.user.domain;

import com.canoacaicara.common.enums.Roles;

public record User(
        String name,
        String email,
        String password,
        String whatsapp,
        String pix,
        Roles role
) {
    public User userWithHashedPassowrd(String hashedPassword) {
        return new User(name, email, hashedPassword,whatsapp, pix, role);
    }
}
