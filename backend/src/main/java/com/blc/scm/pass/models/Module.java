package com.blc.scm.pass.models;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@TableName(value = "module")
@Data
@NoArgsConstructor
public class Module {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer type;

    private String name; //中文名称

    private String code; //英文名称

    private String description; //描述

    private String createTime; //创建时间

    private String updateTime; //更新时间

//    private CustomField customField; //一对一的被维系方

}
