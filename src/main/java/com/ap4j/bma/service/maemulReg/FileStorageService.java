//package com.ap4j.bma.service.maemulReg;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class FileStorageService {
//    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
//
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadDir;
//
//    public String storeFile(MultipartFile file, String fileNamePrefix) {
//        try {
//            String fileName = fileNamePrefix + "-" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
//            File targetFile = new File(uploadDir, fileName);
//
//            // 파일을 지정된 디렉토리에 저장
//            file.transferTo(targetFile);
//
//            // 업로드된 파일의 URL을 반환
//            return "/uploads/" + fileName;
//        } catch (IOException e) {
//            logger.error("파일 업로드 중 오류 발생: " + e.getMessage(), e);
//            throw new RuntimeException("파일 업로드 중 오류 발생");
//        }
//    }
//}
