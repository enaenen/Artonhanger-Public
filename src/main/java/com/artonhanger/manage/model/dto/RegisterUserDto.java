package com.artonhanger.manage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterUserDto {
    private String email;
    private String nickname;
    private String name;
    private String password;
//    private BankList banks;
    private String banks;
    private String accountNumber;
    private String phoneNumber;
    private String introduction;
    private MultipartFile profileImage;
}
