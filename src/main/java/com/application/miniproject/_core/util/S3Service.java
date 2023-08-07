package com.application.miniproject._core.util;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class S3Service {
//    private final AmazonS3Client amazonS3Client;
////    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//    public String uploadImage(MultipartFile file) throws RuntimeException, IOException {
//        String fileUri = null;
//        String fileName = getFileName(file);
//        String contentType = getContentType(fileName.substring(fileName.lastIndexOf(".")));
//
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(contentType);
//        metadata.setContentLength(file.getSize());
//
//        // PutObjectRequest 이용하여 파일 생성 없이 바로 업로드
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        // URL 받아올 때 한글 파일명 깨짐 방지
//        fileUri = URLDecoder.decode(amazonS3Client.getUrl(bucket, fileName).toString(), StandardCharsets.UTF_8);
//        return fileUri;
//    }
//
//    public String getFileName(MultipartFile file) {
//        // Objects.requireNonNull 을 사용할 수도 있음
//        if (Objects.isNull(file) || file.isEmpty()) {
//            throw new IllegalArgumentException("file이 존재하지 않습니다.");
//        }
//
//        String fileName = file.getOriginalFilename().toLowerCase();
//        String uuid = UUID.randomUUID().toString();
//        return uuid + "-" + fileName;
//    }
//
//    public String getContentType(String extension) {
//        String contentType = "";
//        // content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되어
//        // 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨
//        switch (extension) {
//            case ".png":
//                contentType = "image/png";
//                break;
//            case ".jpg":
//            case ".jpeg":
//                contentType = "image/jpeg";
//                break;
//            case ".bmp":
//                contentType = "image/bmp";
//                break;
//            default:
//                contentType = "application/octet-stream";
//                break;
//        }
//        return contentType;
//    }
//
//    // 이미지 삭제
//    public void deleteFile(String fileUrl) {
//        // uri에서 파일명만 추출
//        amazonS3Client.deleteObject(bucket, fileUrl.split("/")[3]);
//    }
//
//
//    // 이미지 수정 -> 기존 이미지 삭제 후 새 이미지 업로드
//    public String updateImage(String deleteImageUri, MultipartFile newFile) throws RuntimeException, IOException {
//        // 기존 파일 삭제
//        if (deleteImageUri != null) {
//            deleteFile(deleteImageUri);
//        }
//        // 새 이미지 업로드
//        return uploadImage(newFile); // url string 리턴
//    }
//}
