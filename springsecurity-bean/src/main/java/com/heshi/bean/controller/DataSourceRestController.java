package com.heshi.bean.controller;

import com.heshi.bean.service.DataSourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/base/DataSource")
public class DataSourceRestController {

    @Resource
    private DataSourceService dataSourceService;

    @RequestMapping("/")
    public String test() {
        return dataSourceService.test();
    }
}
