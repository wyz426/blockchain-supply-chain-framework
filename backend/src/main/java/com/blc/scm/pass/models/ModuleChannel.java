package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "module_channel")
public class ModuleChannel {
    private Integer channelId;

    private Integer moduleId;
}
