package com.artonhanger.manage.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ArtworkSize {
    private double height;
    private double width;
}
