package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.repository.MaemulRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MaemulRegService {

    private final MaemulRegRepository maemulRegRepository;

    @Autowired
    public MaemulRegService(MaemulRegRepository maemulRegRepository) {
        this.maemulRegRepository = maemulRegRepository;
    }

    // 매물 정보 저장
    @Transactional
    public MaemulRegEntity saveMaemulInfo(MaemulRegEntity maemulRegEntity) {
        return maemulRegRepository.save(maemulRegEntity);
    }

    public MaemulRegEntity getMaemulById(Integer maemulId) {
        // 매물 ID로 매물 정보를 조회
        return maemulRegRepository.findById(maemulId).orElse(null);
    }
}