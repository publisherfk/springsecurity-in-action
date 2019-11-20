package com.heshi.bean.service.impl;

import com.heshi.bean.service.DataSourceService;
import org.springframework.stereotype.Service;

/**
 * @author by fukun
 */
@Service("dataSourceServiceImpl")
public class DataSourceServiceImpl implements DataSourceService {
    @Override
    public String test() {
        return "DataSourceServiceImpl";
    }
}
