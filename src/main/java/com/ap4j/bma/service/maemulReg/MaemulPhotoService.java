package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulPhotoEntity;
import com.ap4j.bma.model.repository.MaemulPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class MaemulPhotoService {
    @Autowired
    private MaemulPhotoRepository maemulPhotoRepository;

    public void saveImage(MultipartFile file, MaemulPhotoEntity maemulPhotoEntity) throws IOException {
        // 파일을 저장할 경로를 지정
        String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "maemulPhoto";

        // 업로드된 파일의 원래 파일 이름을 가져옴
        String originalFileName = file.getOriginalFilename();

        // 파일을 저장할 경로와 파일 이름을 결합하여 파일 저장 경로 생성
        String filePath = uploadDir + originalFileName;

        // 이미지 파일을 저장
        file.transferTo(new File(filePath));

        // MaemulPhotoEntity에 이미지 경로 설정
        maemulPhotoEntity.setPhotoPath(filePath);

        // MaemulPhotoEntity를 데이터베이스에 저장
        maemulPhotoRepository.save(maemulPhotoEntity);
    }
}

