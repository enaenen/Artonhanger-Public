package com.artonhanger.manage.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;

/*

 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CollectorContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;                                        // 고유번호

    private String collectorName;
    private String artworkTitle;
    private String contentTitle;

    @Embedded
    private Introduce introduce;
    @Embedded
    private Taste taste;
    @Embedded
    private Location location;
    @Embedded
    private ChoiceStandard choiceStandard;
    @Embedded
    private ChoiceReason choiceReason;
    @Embedded
    private CharmingPoint charmingPoint;
    @Embedded
    private LocationAfter locationAfter;

    private String tip;
    private String recommend;
    private String etc;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdAt;                        // 등록 일자

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updatedAt;                        // 수정 일자

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Introduce {
        @Column(name = "introduce")
        private String content;
        @Column(name = "introduce_url")
        private String url;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Taste {
        @Column(name = "taste")
        private String content;
        @Column(name = "taste_url")
        private String url;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Location {
        @Column(name = "location")
        private String content;
        @Column(name = "location_url")
        private String url;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class ChoiceStandard {
        @Column(name = "choice_standard")
        private String content;
        @Column(name = "choice_standard_url")
        private String url;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class ChoiceReason {
        @Column(name = "choice_reason")
        private String content;
        @Column(name = "choice_reason_url")
        private String url;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class CharmingPoint {
        @Column(name = "charming_point")
        private String content;
        @Column(name = "charming_point_url")
        private String url;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class LocationAfter {
        @Column(name = "location_after")
        private String content;
        @Column(name = "location_after_url")
        private String url;
    }
}
