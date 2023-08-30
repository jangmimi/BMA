package com.ap4j.bma.service.customerCenter;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.model.repository.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QnAService {

    //1:1 문의내역 작성한 것 DB저장 처리
    @Autowired
    private QnARepository qnARepository;

    public QnAEntity saveQnA(QnAEntity qnAEntity) {
        QnAEntity savedEntity = qnARepository.save(qnAEntity);

        return savedEntity;
    }
}
