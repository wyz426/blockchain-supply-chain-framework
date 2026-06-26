package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.CustomField;
import com.blc.scm.pass.models.ModuleField;

import java.util.List;

public interface ModuleFieldService extends IService<ModuleField> {
    void createModuleFieldRelation(Integer moduleId, Integer id);

    List<Integer> getCustomFieldIdsByModuleId(Integer moduleId);

    List<CustomField> getCustomFieldsByIdsAndObjectCode(List<Integer> customFieldIds, String objectCode);

    void deleteModuleFieldRelationByFieldId(Integer fieldId);
}
