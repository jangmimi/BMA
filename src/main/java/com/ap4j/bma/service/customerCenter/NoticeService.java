package com.ap4j.bma.service.customerCenter;

import com.ap4j.bma.model.entity.customerCenter.NoticeEntity;
import com.ap4j.bma.model.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    public List<NoticeEntity> getAllNotices() {

        return noticeRepository.findAll();
    }

    public NoticeEntity noticeView(Integer id) {
        return noticeRepository.findById(id).get();
    }

    public Page<NoticeEntity> getNoticePage(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}