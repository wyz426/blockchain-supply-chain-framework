package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "module_field")
public class ModuleField {
    private Integer fieldId;

    private Integer moduleId;
}
