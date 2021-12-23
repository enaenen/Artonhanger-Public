package com.artonhanger.manage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordModifyDto {
    private String email;
    private String changedPassword;
    private String prevPassword;
}
