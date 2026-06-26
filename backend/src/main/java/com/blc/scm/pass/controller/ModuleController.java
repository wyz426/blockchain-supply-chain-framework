package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.Module;
import com.blc.scm.pass.services.Impl.ModuleServiceImpl;
import com.blc.scm.pass.services.Impl.SystemModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController {
    private final ModuleServiceImpl moduleServiceImpl;

    private final SystemModuleServiceImpl systemModuleService;

    @Autowired
    public ModuleController(ModuleServiceImpl moduleServiceImpl, SystemModuleServiceImpl systemModuleService) {
        this.moduleServiceImpl = moduleServiceImpl;
        this.systemModuleService = systemModuleService;
    }

    @PostMapping("/")
    public Module createModule(@RequestBody Module module, @RequestParam("systemId") Integer systemId){
        //id自增 传过来的只有Type 所以要在后端根据type补充Module的属性
        moduleServiceImpl.setModuleAttributesByType(module);
        moduleServiceImpl.save(module);
        systemModuleService.createSystemModuleRelation(systemId, module.getId());
        return module;
    }

    @GetMapping("/system/{systemId}")
    public List<Module> getModulesBySystemId(@PathVariable Integer systemId) {
        return systemModuleService.getModulesBySystemId(systemId);
    }
}
