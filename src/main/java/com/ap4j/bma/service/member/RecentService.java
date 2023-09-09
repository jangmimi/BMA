package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.entity.recentView.RecentEntity;
import com.ap4j.bma.model.repository.RecentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecentService {
    @Autowired
    private RecentRepository recentRepository;

//    public Long countAll() {
//        return recentRepository.count();
//    }
//
//    public List<RecentEntity> getAllList() {
//        return recentRepository.findAll();
//    }

//    public Page<RecentEntity> getPaginatedItems (MemberDTO dto, int page, int pageSize) {
//        RecentEntity recentEntity = new RecentEntity();
//        recentEntity.setMemberEntity(dto.toEntity());
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        recentRepository.findRecentMaemul(recentEntity,pageable);
//
//        return null;
//    }
}
