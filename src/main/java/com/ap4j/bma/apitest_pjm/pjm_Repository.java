package com.ap4j.bma.apitest_pjm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface pjm_Repository extends JpaRepository<pjm_UserInfo, Long> {

//    Optional<pjm_UserInfo> findByName(String name);


}
