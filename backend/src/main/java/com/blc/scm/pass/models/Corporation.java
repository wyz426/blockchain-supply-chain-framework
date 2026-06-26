package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// TableName 需要引入mybatis-plus
@TableName(value = "corporation")
// 这个对象也对应一个表中存储的对象
@Data
public class Corporation {
    @TableId(type = IdType.AUTO) // 声明这个为数据库中的主键， 后面的AUTO代表主键自动增长（唯一标识数据库中的数据）
    private Integer id; // 自增id

    private String user_uuid; // 企业id

    private String name; //企业名称

    private String status; // 状态(启用/停用/待审核)

    private String description; //企业描述及经营范围

    private String credit_code; // 企业的统一信用代码

    private String post_code; // 邮编

    private String address; // 地址

    private String createdTime; // 创建时间

    private String updatedTime; // 修改时间

}
