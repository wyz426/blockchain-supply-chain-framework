package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// TableName 需要引入mybatis-plus
@TableName(value = "user")
// 这个对象也对应一个表中存储的对象
@Data
public class User {
    @TableId(type = IdType.AUTO) // 声明这个为数据库中的主键， 后面的AUTO代表主键自动增长（唯一标识数据库中的数据）
    private Integer id; // 自增id

    private String user_uuid; // 企业id/管理员id

    private String email; // 企业邮箱/管理员邮箱

    private String password;

    private String role; // 账号类型（管理员/普通企业/监管组织）

    private String created_time; // 创建时间

    private String updated_time; // 修改时间

}
