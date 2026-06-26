package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "corporation_blockchain")
// 这个对象也对应一个表中存储的对象
@Data
public class CorporationBlockchain {
    private Integer CorporationId;
    private Integer BlockchainId;
    private String identifier;
}
