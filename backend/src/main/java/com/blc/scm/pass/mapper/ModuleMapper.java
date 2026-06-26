package com.blc.scm.pass.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blc.scm.pass.models.Module;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModuleMapper extends BaseMapper<Module> {
    void insertBatch(List<Module> moduleInfos);
}
