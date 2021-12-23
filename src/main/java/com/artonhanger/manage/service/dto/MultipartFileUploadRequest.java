package com.artonhanger.manage.service.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
public class MultipartFileUploadRequest {
    private final MultipartFile uploadMultipartFile;
    private final String fileName;
    private final String dirName;

    private MultipartFileUploadRequest(Builder builder) {
        this.uploadMultipartFile = builder.uploadMultipartFile;
        this.fileName = builder.fileName;
        this.dirName = builder.dirName;
    }

    public static Builder builderWithFile(MultipartFile uploadMultipartFile) {
        return new Builder(uploadMultipartFile);
    }

    public static class Builder {
        private final MultipartFile uploadMultipartFile;
        private String fileName;
        private String dirName;

        private Builder(MultipartFile uploadMultipartFile) {
            this.uploadMultipartFile = uploadMultipartFile;
            this.fileName = uploadMultipartFile.getName();
            this.dirName = "";
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder dirName(String dirName) {
            this.dirName = dirName;
            return this;
        }

        public MultipartFileUploadRequest build() {
            return new MultipartFileUploadRequest(this);
        }
    }
}
