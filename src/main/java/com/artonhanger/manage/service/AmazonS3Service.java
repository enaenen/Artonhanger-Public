package com.artonhanger.manage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.service.dto.FileUploadRequest;
import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import com.artonhanger.manage.service.dto.UploadResult;
import com.artonhanger.manage.utils.PathUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonS3Service implements ObjectStorageService {
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.domain-uri}")
    private String domainUri;

    @Override
    public UploadResult upload(FileUploadRequest request) {
        String fullFilePath = PathUtil.replaceWindowPathToLinuxPath(
                Paths.get(request.getDirName(), request.getFileName()).toString()
        );

        s3Client.putObject(new PutObjectRequest(bucket, fullFilePath, request.getUploadFile())
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return UploadResult.builder()
                .downloadableUrl(s3Client.getUrl(bucket, fullFilePath).toString())
                .build();
    }

    @Override
    public UploadResult upload(MultipartFileUploadRequest request) {
        String fullFilePath = PathUtil.replaceWindowPathToLinuxPath(
                Paths.get(request.getDirName(), request.getFileName()).toString()
        );
        S3ObjectUploadDto s3ObjectUploadDto = buildObjectUploadDto(request.getUploadMultipartFile());

        s3Client.putObject(new PutObjectRequest(
                bucket, fullFilePath, s3ObjectUploadDto.getByteArrayInputStream(), s3ObjectUploadDto.getObjectMetadata()
                ).withCannedAcl(CannedAccessControlList.PublicRead));

        return UploadResult.builder()
                .downloadableUrl(s3Client.getUrl(bucket, fullFilePath).toString())
                .build();
    }

    private S3ObjectUploadDto buildObjectUploadDto(MultipartFile file) {
        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentType(Mimetypes.getInstance().getMimetype(file.getName()));
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentLength(bytes.length);
            return new S3ObjectUploadDto(
                    new ByteArrayInputStream(bytes),
                    objMeta
            );
        } catch (IOException e) {
            throw new AOHException(ErrorEnum.ETC, e);
        }
    }

    @Override
    public void remove(String fileName) {
        s3Client.deleteObject(bucket,fileName);
    }

    @Getter
    @RequiredArgsConstructor
    private static class S3ObjectUploadDto {
        private final ByteArrayInputStream byteArrayInputStream;
        private final ObjectMetadata objectMetadata;
    }
}
