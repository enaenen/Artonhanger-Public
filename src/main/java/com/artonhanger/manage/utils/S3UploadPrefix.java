package com.artonhanger.manage.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3UploadPrefix {
    public static String ARTWORK;
    public static String PROFILE;
    public static String PROFILE_KAKAO;

    @Value("${cloud.aws.s3.prefix.profile-image.default}")
    public void setPROFILE(String PROFILE) {
        this.PROFILE = PROFILE;
    }

    @Value("${cloud.aws.s3.prefix.profile-image.kakao}")
    public void setPROFILE_KAKAO(String PROFILE_KAKAO) {
        this.PROFILE_KAKAO = PROFILE_KAKAO;
    }
    @Value("${cloud.aws.s3.prefix.artwork-image}")
    public void setARTWORK(String ARTWORK) {
        this.ARTWORK = ARTWORK;
    }
}
