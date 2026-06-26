package com.blc.scm.pass.models;

import lombok.Data;

import java.util.List;

/**
 * @author Elaine Huang
 * @date 2024/4/2 12:51 AM
 * @signature Do it while you can!
 */
@Data
public class SysBuildModel {
   private SystemEntity systemInfo;
   private List<Module> moduleInfos;
}
