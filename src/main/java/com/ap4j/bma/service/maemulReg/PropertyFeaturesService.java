package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.PropertyFeatures;
import com.ap4j.bma.model.repository.PropertyFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PropertyFeaturesService {
    private final PropertyFeaturesRepository propertyFeaturesRepository;

    @Autowired
    public PropertyFeaturesService(PropertyFeaturesRepository propertyFeaturesRepository) {
        this.propertyFeaturesRepository = propertyFeaturesRepository;
    }

    @Transactional
    public PropertyFeatures savePropertyFeatures(PropertyFeatures propertyFeatures) {
        return propertyFeaturesRepository.save(propertyFeatures);
    }
}