package com.artonhanger.manage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Getter
public class DbMetaProperty {
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();                        // 등록 일자

    @Column(length = 20)
    private LocalDateTime updatedAt = LocalDateTime.now();                        // 수정 일자

    @Setter
    @Column(name = "is_enable")
    private boolean enable = true;                               // 사용 여부

    @PrePersist
    public void onCreation() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
