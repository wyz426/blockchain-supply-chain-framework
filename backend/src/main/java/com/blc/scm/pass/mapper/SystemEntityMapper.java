package com.blc.scm.pass.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blc.scm.pass.models.SystemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemEntityMapper extends BaseMapper<SystemEntity> {

}