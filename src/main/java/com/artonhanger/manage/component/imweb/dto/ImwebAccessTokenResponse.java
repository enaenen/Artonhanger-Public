package com.artonhanger.manage.component.imweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString()
@NoArgsConstructor
@AllArgsConstructor
public class ImwebAccessTokenResponse {
    private String msg;
    private Integer code;
    @JsonProperty(value = "access_token")
    private String accessToken;
}
