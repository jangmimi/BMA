package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.repository.MaemulRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaemulRegService {

    @Autowired
    private MaemulRegRepository maemulRegRepository;

    public MaemulRegEntity saveMaemulInfo(MaemulRegEntity maemulRegEntity) {
        // 매물 정보를 데이터베이스에 저장
        return maemulRegRepository.save(maemulRegEntity);
    }
}
