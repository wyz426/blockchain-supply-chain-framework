package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.Corporation;
import org.springframework.stereotype.Service;

public interface CorporationService extends IService<Corporation> {
    Corporation getByUid(String uid);
}
