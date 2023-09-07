package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaemulPhotoRepository extends JpaRepository<MaemulPhotoEntity, Integer> {
    MaemulPhotoEntity findByMaemulID(Integer maemulId);
}
