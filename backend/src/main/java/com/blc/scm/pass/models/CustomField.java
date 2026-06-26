package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@TableName("custom_field")
public class CustomField {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer moduleId;

    // 标记是哪一个实体
    private String objectCode;

    // 字段名称（中文名称）
    private String fieldName;

    // 字段编码（英文标识）
    private String fieldCode;

    // 字段类型
    private String fieldType;

    // 字段注释
    private String fieldRemark;

    // 创建时间
    private String createTime;

    // 修改时间
    private String updateTime;

    private String source;

    private String isnecessary;
}
