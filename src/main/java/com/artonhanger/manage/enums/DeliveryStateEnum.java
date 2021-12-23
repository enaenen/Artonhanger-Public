package com.artonhanger.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeliveryStateEnum {
    ONSALE("ONSALE"),
    UNPAIED("UNPAIED"),
    PAIED("PAIED"),
    PREDELIVERY("PREDELIVERY"),
    ONDELIVERY("in_transit"),
    CONCLUDED("delivered"),
    CANCLED("CANCLED"),
    PREUPLOAD("PREUPLOAD");

    private String value;

    public String getAuthority() {
        return this.getValue();
    }
}


