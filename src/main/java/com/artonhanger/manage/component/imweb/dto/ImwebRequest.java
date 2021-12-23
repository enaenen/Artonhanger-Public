package com.artonhanger.manage.component.imweb.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImwebRequest {
    private final String uri;
    private final Object body;
}
