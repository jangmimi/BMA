package com.ap4j.bma.service.customerCenter;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.model.repository.QnARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class QnAService {

    //1:1 문의내역 작성한 것 DB저장 처리
    @Autowired
    private QnARepository qnARepository;

    public void saveQnA(QnAEntity qnAEntity, MultipartFile file) throws Exception {

        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "qnaFile";

        UUID uuid = UUID.randomUUID();

        if(file == null || file.isEmpty()){

            qnAEntity.setFilename(null);
            qnAEntity.setFilepath(null);

        }else{
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(path, fileName);
            file.transferTo(saveFile);

            qnAEntity.setFilename(fileName);
            qnAEntity.setFilepath("/qnaFile/" + fileName);
        }
        
        qnARepository.save(qnAEntity);

    }

    // 문의글 보기  - ID에 해당하는 글을 조회하고 반환
    public QnAEntity findById(Integer id) {
        return qnARepository.findById(id).orElse(null);
    }

    // 게시글 삭제
    public void deleteQnA(Integer id) {
        qnARepository.deleteById(id);
    }


}
