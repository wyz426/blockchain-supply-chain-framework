package com.blc.scm.pass.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// TableName 需要引入mybatis-plus
@TableName(value = "blockchain_org")
// 这个对象也对应一个表中存储的对象
@Data
@Getter
@Setter
public class BlockChainMember {//联盟成员表
    @TableId(type = IdType.AUTO) // 声明这个为数据库中的主键， 后面的AUTO代表主键自动增长（唯一标识数据库中的数据）
    private Integer id; // 自增id

    private String blockchain_id;
    private String blockchainName;

    private String user_uuid; // 联盟成员企业uid
    private String userName; // 联盟成员企业名称

    private String name; // 组织名称

    private String description; // 角色（原材料供应商/...）

    private Integer status ; // 运行状态

    private String createdTime; // 创建时间

    private String updatedTime; // 修改时间

    public BlockChainMember(){
        this.status=0;
    }
}
