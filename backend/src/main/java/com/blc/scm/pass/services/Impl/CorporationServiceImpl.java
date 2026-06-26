package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.models.Corporation;
import com.blc.scm.pass.services.CorporationService;
import com.blc.scm.pass.mapper.CorporationMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class CorporationServiceImpl extends ServiceImpl<CorporationMapper, Corporation> implements CorporationService {
    private final CorporationMapper corporationMapper;

    public CorporationServiceImpl(CorporationMapper corporationMapper) {
        this.corporationMapper = corporationMapper;
    }

    @Override
    public Corporation getByUid(String uid) {
        QueryWrapper<Corporation>wrapper=new QueryWrapper<>();
        wrapper.eq("user_uuid",uid);
        if(Objects.isNull(wrapper))return new Corporation();//如果没有符合要求的内容，即还未提交企业信息
        else return corporationMapper.selectList(wrapper).get(0);
    }
}
