package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("chaincode")
public class Chaincode {
    @TableId(type = IdType.AUTO)
    Integer id;

    String name;

    String description;

    Integer status;

    String version;

    String path;
}
