package com.artonhanger.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum UserRole implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"),
    ARTDELIVERY("ROLE_ARTDELIVERY"),
    ARTIST("ROLE_ARTIST"),
    USER("ROLE_USER"),
    SOCIAL("ROLE_SOCIAL");
    private String value;

    @Override
    public String getAuthority() {
        return this.getValue();
    }
}