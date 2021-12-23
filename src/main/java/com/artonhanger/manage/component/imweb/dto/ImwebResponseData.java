package com.artonhanger.manage.component.imweb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"imwebId"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImwebResponseData {
    @JsonProperty(value = "prod_no")
    private String imwebId;
    @JsonProperty(value = "stock")
    private ImwebResponseStock stock;
}

