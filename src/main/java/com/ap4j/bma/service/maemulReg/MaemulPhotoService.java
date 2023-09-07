package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulPhotoEntity;
import com.ap4j.bma.model.repository.MaemulPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class MaemulPhotoService {

    @Autowired
    private MaemulPhotoRepository maemulPhotoRepository;

    @Autowired
    private FileStorageService fileStorageService;


    @Transactional
    public void saveImage(MultipartFile file, MaemulPhotoEntity maemulPhotoEntity) throws IOException {
        // 이미지 파일을 업로드 폴더에 저장하고 URL을 반환
        String imagePath = fileStorageService.storeFile(file, "photo");

        // 이미지 경로를 MaemulPhotoEntity에 추가
        maemulPhotoEntity.addImagePath(imagePath);

        // 수정된 이미지 엔티티를 저장
        maemulPhotoRepository.save(maemulPhotoEntity);
    }
}