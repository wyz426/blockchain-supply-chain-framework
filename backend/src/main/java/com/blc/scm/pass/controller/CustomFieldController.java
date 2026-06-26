package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.CustomField;
import com.blc.scm.pass.services.Impl.CustomFieldServiceImpl;
import com.blc.scm.pass.services.Impl.ModuleFieldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customField")
public class CustomFieldController {
    @Autowired
    private final CustomFieldServiceImpl customFieldService;

    @Autowired
    private final ModuleFieldServiceImpl moduleFieldService;

    public CustomFieldController(CustomFieldServiceImpl customFieldService, ModuleFieldServiceImpl moduleFieldService) {
        this.customFieldService = customFieldService;
        this.moduleFieldService = moduleFieldService;
    }

    @PostMapping("/")
    public CustomField createCustomField(@RequestBody CustomField customField, @RequestParam("moduleId") Integer moduleId) {
        customFieldService.save(customField);
        moduleFieldService.createModuleFieldRelation(moduleId, customField.getId());
        return customField;
    }

    @PostMapping("/edit")
    public CustomField editCustomField(@RequestBody CustomField customField) {
        customFieldService.updateById(customField);
        return customField;
    }

    @DeleteMapping("/del/{fieldId}")
    public CustomField deleteCustomField(@PathVariable("fieldId") Integer fieldId) {
        // 删除关联表中带有fieldId的记录
        moduleFieldService.deleteModuleFieldRelationByFieldId(fieldId);

        // 删除定制字段表中的记录
        CustomField customField = customFieldService.getById(fieldId);
        if (customField != null) {
            customFieldService.removeById(fieldId);
        }

        return customField;
    }

    @GetMapping("/module")
    public List<CustomField> getCustomFields(
            @RequestParam("moduleId") Integer moduleId,
            @RequestParam("objectCode") String objectCode
    ) {
        List<Integer> customFieldIds = moduleFieldService.getCustomFieldIdsByModuleId(moduleId);
        return moduleFieldService.getCustomFieldsByIdsAndObjectCode(customFieldIds, objectCode);
    }

}
