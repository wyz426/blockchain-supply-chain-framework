package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

// TableName 需要引入mybatis-plus
@TableName(value = "Application")
// 这个对象也对应一个表中存储的对象
@Data
public class Application {
    @TableId(type = IdType.AUTO) // 声明这个为数据库中的主键， 后面的AUTO代表主键自动增长（唯一标识数据库中的数据）
    private Integer id; // 自增id

    private Integer blockchainId;

    private String app_uuid; // 应用id

    private String key_uuid; // 应用密钥

    private String app_name; // 名称

    private String description; // 描述

    private String status; // 状态(启用/停用)

    private String icon; // 图标链接

    private String group_id; // 所属组织的id

    private String address; // 访问地址

    private String db; // 数据库名称

    private String creator; // 创建人

    private String created_time; // 创建时间

    private String updated_time; // 修改时间

}
