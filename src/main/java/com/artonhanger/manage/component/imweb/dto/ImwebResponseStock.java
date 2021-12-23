package com.artonhanger.manage.component.imweb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"stockAmount"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImwebResponseStock {
    @JsonProperty(value = "stock_no_option")
    private int stockAmount;
}
