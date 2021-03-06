package com.simso.domain.user.dto;

import com.simso.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private String password;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
