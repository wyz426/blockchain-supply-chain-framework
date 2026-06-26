package com.blc.scm.pass.controller;

import com.blc.scm.pass.models.Application;
import com.blc.scm.pass.services.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Application> getApplications() {
        return applicationService.list();
    }

    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable("id") Long id) {
        return applicationService.getById(id);
    }

    @PostMapping("/add")
    public Application createApplication(@RequestBody Application application) {
        applicationService.save(application);
        return application;
    }

    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable("id") Integer id, @RequestBody Application application) {
            application.setId(id);
        applicationService.updateById(application);
        return application;
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable("id") Long id) {
        applicationService.removeById(id);
    }
}