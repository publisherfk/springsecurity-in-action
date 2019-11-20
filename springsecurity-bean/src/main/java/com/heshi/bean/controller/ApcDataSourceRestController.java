package com.heshi.bean.controller;

import com.heshi.bean.service.TagManagementDataSourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/base/ApcDataSource")
public class ApcDataSourceRestController {
    @Resource
    private TagManagementDataSourceService dataSourceService;

    @RequestMapping("/")
    public String test() {
        return dataSourceService.test();
    }
}
