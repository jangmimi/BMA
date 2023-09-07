//package com.ap4j.bma.service.maemulReg;
//
//import com.ap4j.bma.model.entity.meamulReg.MaemulPhotoEntity;
//import com.ap4j.bma.model.repository.MaemulPhotoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.transaction.Transactional;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class MaemulPhotoService {
//    private final MaemulPhotoRepository maemulPhotoRepository;
//
//    @Autowired
//    public MaemulPhotoService(MaemulPhotoRepository maemulPhotoRepository) {
//        this.maemulPhotoRepository = maemulPhotoRepository;
//    }
//
//    @Transactional
//    public void saveMaemulPhotos(MaemulPhotoEntity maemulPhotoEntity, MultipartFile file) throws IOException {
//
//        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "maemulFile";
//
//        UUID uuid = UUID.randomUUID();
//
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        File saveFile = new File(path, fileName);
//
//        file.transferTo(saveFile);
////
////        maemulPhotoEntity.setFilename(fileName);
////        maemulPhotoEntity.setFilepath("/maemulPhoto/" + fileName);
//
//
//        maemulPhotoRepository.save(maemulPhotoEntity);
//    }
//}
