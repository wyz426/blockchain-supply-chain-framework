package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

// TableName 需要引入mybatis-plus
@TableName(value = "blockchain")
// 这个对象也对应一个表中存储的对象
@Data
public class BlockChain {//联盟信息表
    @TableId(type = IdType.AUTO) // 声明这个为数据库中的主键， 后面的AUTO代表主键自动增长（唯一标识数据库中的数据）
    private Integer id; // 自增id

    private String user_uuid; // 联盟uid

    private String name; // 联盟名称

    private String description; // 描述

    private Integer status; // 运行状态
    private String createdTime; // 创建时间

    private String updatedTime; // 修改时间



}
