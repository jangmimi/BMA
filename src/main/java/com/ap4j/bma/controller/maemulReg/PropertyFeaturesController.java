package com.ap4j.bma.controller.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.PropertyFeatures;
import com.ap4j.bma.service.maemulReg.PropertyFeaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PropertyFeaturesController {
    private final PropertyFeaturesService propertyFeaturesService;

    @Autowired
    public PropertyFeaturesController(PropertyFeaturesService propertyFeaturesService) {
        this.propertyFeaturesService = propertyFeaturesService;
    }

    @PostMapping("/savePropertyFeatures")
    @ResponseBody
    public PropertyFeatures savePropertyFeatures(@RequestBody PropertyFeatures propertyFeatures) {
        return propertyFeaturesService.savePropertyFeatures(propertyFeatures);
    }
}