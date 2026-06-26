package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.Module;

public interface ModuleService extends IService<Module> {
    public void setModuleAttributesByType(Module module);

    public void setAttributesForType1(Module module);

    public void setAttributesForType2(Module module);

    public void setAttributesForType3(Module module);

    public void setAttributesForType4(Module module);

    public void setAttributesForType5(Module module);

    public void setAttributesForType6(Module module);

    public void setAttributesForType7(Module module);

    public void setAttributesForType8(Module module);

    public void setAttributesForType9(Module module);

    public void setAttributesForType10(Module module);




}
