package com.artonhanger.manage.service.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
public class FileUploadRequest {
    private final File uploadFile;
    private final String fileName;
    private final String dirName;

    private FileUploadRequest(Builder builder) {
        this.uploadFile = builder.uploadFile;
        this.fileName = builder.fileName;
        this.dirName = builder.dirName;
    }

    public static Builder builderWithFile(File uploadFile) {
        return new Builder(uploadFile);
    }

    public static class Builder {
        private final File uploadFile;
        private String fileName;
        private String dirName;

        private Builder(File uploadFile) {
            this.uploadFile = uploadFile;
            this.fileName = uploadFile.getName();
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

        public FileUploadRequest build() {
            return new FileUploadRequest(this);
        }
    }
}
