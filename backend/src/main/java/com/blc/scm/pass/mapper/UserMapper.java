package com.blc.scm.pass.mapper;

import com.blc.scm.pass.models.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

// 继承了BaseMapper，就可以直接操作数据库了
// BaseMapper < T > 指定了对哪个数据类型进行map （我们）

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
