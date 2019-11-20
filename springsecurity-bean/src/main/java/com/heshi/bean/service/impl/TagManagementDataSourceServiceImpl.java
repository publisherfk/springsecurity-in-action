package com.heshi.bean.service.impl;

import com.heshi.bean.service.TagManagementDataSourceService;
import org.springframework.stereotype.Service;

/**
 * @author by fukun
 */
@Service("tagManagementDataSourceServiceImpl")
public class TagManagementDataSourceServiceImpl implements TagManagementDataSourceService {
    @Override
    public String test() {
        return "ApcDataSourceServiceImpl";
    }
}
