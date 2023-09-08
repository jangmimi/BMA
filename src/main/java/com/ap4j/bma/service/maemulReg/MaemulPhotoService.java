package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulPhotoEntity;
import com.ap4j.bma.model.repository.MaemulPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class MaemulPhotoService {
    @Autowired
    private MaemulPhotoRepository maemulPhotoRepository;

    public void saveImage(MultipartFile file, MaemulPhotoEntity maemulPhotoEntity) throws IOException {
        if (!file.isEmpty()) {
            // 파일을 저장할 경로를 지정
            String uploadDir = "C:/project/src/main/resources/static/maemulPhoto"; // 이미지를 저장할 경로

            // 업로드된 파일의 원래 확장자를 가져옴
            String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            // UUID를 이용해 고유한 파일명 생성
            String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;

            // 파일을 저장할 경로와 파일 이름을 결합하여 파일 저장 경로 생성
            String filePath = uploadDir + File.separator + storedFileName;

            // 이미지 파일을 저장
            file.transferTo(new File(filePath));

            // MaemulPhotoEntity에 이미지 경로 설정
            maemulPhotoEntity.setPhotoPath(filePath);

            // MaemulPhotoEntity를 데이터베이스에 저장 (예를 들어 JPA를 사용하는 경우)
            maemulPhotoRepository.save(maemulPhotoEntity);
        }
    }
}