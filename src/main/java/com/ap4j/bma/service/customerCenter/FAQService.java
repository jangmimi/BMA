package com.ap4j.bma.service.customerCenter;

import com.ap4j.bma.model.entity.customerCenter.FAQEntity;
import com.ap4j.bma.model.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQService {
    @Autowired
    private FAQRepository faqRepository;

    public List<FAQEntity> getAllFAQ() {

        return faqRepository.findAll();
    }

    public FAQEntity faqView(Integer id) {
        return faqRepository.findById(id).get();
    }
}
