package com.artonhanger.manage.model.dto;

import lombok.*;

@NoArgsConstructor
@Getter
public class RegDupCheckDto {
    private String email;
    private String nickname;

    public RegDupCheckDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public RegDupCheckDto(RegisterUserDto registerUserDto){
        this.email = registerUserDto.getEmail();
        this.nickname = registerUserDto.getNickname();
    }
}
