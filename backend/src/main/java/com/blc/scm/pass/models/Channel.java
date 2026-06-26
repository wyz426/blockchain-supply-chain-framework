package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("channel")
public class Channel {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String blockchain_id;

    private String name;

    private String orgs;

    private String description;

    private Integer status;

    private String user_uuid;
}
