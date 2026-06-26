package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.ModuleFieldMapper;
import com.blc.scm.pass.models.CustomField;
import com.blc.scm.pass.models.ModuleField;
import com.blc.scm.pass.services.ModuleFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleFieldServiceImpl extends ServiceImpl<ModuleFieldMapper, ModuleField> implements ModuleFieldService {
    private final ModuleFieldMapper moduleFieldMapper;

    private final CustomFieldServiceImpl customFieldService;

    @Autowired
    public ModuleFieldServiceImpl(ModuleFieldMapper moduleFieldMapper, CustomFieldServiceImpl customFieldService) {
        this.moduleFieldMapper = moduleFieldMapper;
        this.customFieldService = customFieldService;
    }
    @Override
    public void createModuleFieldRelation(Integer moduleId, Integer fieldId) {
        ModuleField moduleField = new ModuleField();
        moduleField.setModuleId(moduleId);
        moduleField.setFieldId(fieldId);
        moduleFieldMapper.insert(moduleField);
    }

    @Override
    public List<Integer> getCustomFieldIdsByModuleId(Integer moduleId) {
        QueryWrapper<ModuleField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("module_id", moduleId);
        List<ModuleField> moduleFieldList = this.list(queryWrapper);

        // 获取关联的定制字段ID列表
        List<Integer> customFieldIds = new ArrayList<>();
        for (ModuleField moduleField : moduleFieldList) {
            customFieldIds.add(moduleField.getFieldId());
        }
        return customFieldIds;
    }

    @Override
    public List<CustomField> getCustomFieldsByIdsAndObjectCode(List<Integer> customFieldIds, String objectCode) {
        QueryWrapper<CustomField> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", customFieldIds).eq("object_code", objectCode);
        return customFieldService.list(queryWrapper);
    }

    @Override
    public void deleteModuleFieldRelationByFieldId(Integer fieldId) {
        // 构建删除条件
        QueryWrapper<ModuleField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("field_id", fieldId);

        // 删除关联表中的记录
        moduleFieldMapper.delete(queryWrapper);
    }
}
