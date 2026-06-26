package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.Application;
import com.blc.scm.pass.models.SysBuildModel;

public interface ApplicationService extends IService<Application> {

    boolean createSystem(SysBuildModel buildModel);
}
