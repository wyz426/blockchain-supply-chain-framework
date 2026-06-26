package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName(value = "system_entity")
@Data
public class SystemEntity {

    @TableId(type = IdType.AUTO) // 声明这个为数据库中的主键， 后面的AUTO代表主键自动增长（唯一标识数据库中的数据）
    private Integer id; // 自增id

    private String user_uuid;

    private Integer blockchainid;

    private String name;

    private String description;

    private String icon;

    private Integer status;

    private List<Module> modules;

    public Integer getBlockchainId() {
        return this.blockchainid;
    }

    public Integer getBlockchainid() {
        return this.blockchainid;
    }
}
