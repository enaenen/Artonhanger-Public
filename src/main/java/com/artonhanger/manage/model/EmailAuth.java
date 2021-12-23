package com.artonhanger.manage.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity @Getter @Setter
public class EmailAuth {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private int authKey;
    private boolean enable;
    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdTime;
}