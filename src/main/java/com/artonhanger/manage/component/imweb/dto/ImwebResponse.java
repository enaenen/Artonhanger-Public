package com.artonhanger.manage.component.imweb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"msg","code"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImwebResponse {
    private String msg;
    private Integer code;
    @JsonProperty(value = "data")
    private ImwebResponseData responseData;
}
