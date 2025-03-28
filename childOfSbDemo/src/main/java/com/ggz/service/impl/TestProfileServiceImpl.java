package com.ggz.service.impl;

import com.ggz.service.TestProfileService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * 测试@Profile
 *
 * @author ggz on 2022/9/28
 */

@Service
@Profile(value = "prod")
public class TestProfileServiceImpl implements TestProfileService {
    @Override
    public String print() {
        return "prod";
    }
}