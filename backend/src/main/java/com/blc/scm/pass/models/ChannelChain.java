package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("channel_chain")
public class ChannelChain {
    Integer channel_id;
    Integer chaincode_id;
}
