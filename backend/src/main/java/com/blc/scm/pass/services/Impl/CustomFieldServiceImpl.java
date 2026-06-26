package com.blc.scm.pass.services.Impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.CustomFieldMapper;
import com.blc.scm.pass.models.CustomField;
import com.blc.scm.pass.services.CustomFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomFieldServiceImpl extends ServiceImpl<CustomFieldMapper, CustomField> implements CustomFieldService {
    @Autowired
    private final CustomFieldMapper customFieldMapper;

    public CustomFieldServiceImpl(CustomFieldMapper customFieldMapper) {
        this.customFieldMapper = customFieldMapper;
    }


}
