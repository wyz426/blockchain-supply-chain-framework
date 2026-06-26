package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.ModuleMapper;
import com.blc.scm.pass.models.Module;
import com.blc.scm.pass.services.ModuleService;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper,Module> implements ModuleService {


    @Override
    public void setModuleAttributesByType(Module module) {
        switch (module.getType()) {
            case 1:
                setAttributesForType1(module);
                break;
            case 2:
                setAttributesForType2(module);
                break;
            case 3:
                setAttributesForType3(module);
                break;
            case 4:
                setAttributesForType4(module);
                break;
            case 5:
                setAttributesForType5(module);
                break;
            case 6:
                setAttributesForType6(module);
                break;
            case 7:
                setAttributesForType7(module);
                break;
            case 8:
                setAttributesForType8(module);
                break;
            case 9:
                setAttributesForType9(module);
                break;
            case 10:
                setAttributesForType10(module);
                break;
            default:
                // 默认处理方式，可以根据具体需求进行设置
                break;
        }
    }

    @Override
    public void setAttributesForType1(Module module) {
        module.setName("用户管理");
        module.setCode("user");
        module.setDescription("提供系统用户登录、注册及权限控制功能");
    }

    @Override
    public void setAttributesForType2(Module module) {
        module.setName("产品管理");
        module.setCode("product");
        module.setDescription("提供产品信息填报、修改、删除等功能");
    }

    @Override
    public void setAttributesForType3(Module module) {
        module.setName("采购管理");
        module.setCode("purchase");
        module.setDescription("提供供应商信息管理、采购单管理功能");
    }

    @Override
    public void setAttributesForType4(Module module) {
        module.setName("仓储管理");
        module.setCode("warehouse");
        module.setDescription("提供仓库信息管理、填报入库单、填报出库单、查看库存等功能");
    }

    @Override
    public void setAttributesForType5(Module module) {
        module.setName("销售管理");
        module.setCode("sales");
        module.setDescription("提供客户信息管理、销售订单管理功能");
    }

    @Override
    public void setAttributesForType6(Module module) {
        module.setName("物流管理");
        module.setCode("logistics");
        module.setDescription("提供物流公司信息管理、发货信息管理功能");
    }

    @Override
    public void setAttributesForType7(Module module) {
        module.setName("生产管理");
        module.setCode("production");
        module.setDescription("生产管理的描述");
    }

    @Override
    public void setAttributesForType8(Module module) {
        module.setName("加工管理");
        module.setCode("processing");
        module.setDescription("加工管理的描述");
    }

    @Override
    public void setAttributesForType9(Module module) {
        module.setName("质检管理");
        module.setCode("qualityInspection");
        module.setDescription("质检管理的描述");
    }

    @Override
    public void setAttributesForType10(Module module) {
        module.setName("溯源管理");
        module.setCode("trace");
        module.setDescription("溯源管理的描述");
    }
}
