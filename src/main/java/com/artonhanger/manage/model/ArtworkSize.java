package com.artonhanger.manage.model;

import lombok.*;

import javax.persistence.Embeddable;

@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ArtworkSize {
    private double height;
    private double width;
}
