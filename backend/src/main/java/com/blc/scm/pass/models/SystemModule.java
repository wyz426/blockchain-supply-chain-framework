package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "system_module")
public class SystemModule {

    private Integer systemId;

    private Integer moduleId;


    // 其他属性和方法...
}
