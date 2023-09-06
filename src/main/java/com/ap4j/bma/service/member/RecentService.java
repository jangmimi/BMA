package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.RecentEntity;
import com.ap4j.bma.model.repository.RecentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecentService {
    @Autowired
    private RecentRepository recentRepository;

    public Long countAll() {
        return recentRepository.count();
    }

    public List<RecentEntity> getAllList() {
        return recentRepository.findAll();
    }
}
