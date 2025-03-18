package com.ggz.service.impl;

import com.ggz.service.TicketService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.Executors;

/**
 * @author ggz on 2022/9/19
 */

@DubboService
public class TicketServiceImpl implements TicketService {
    @Override
    public String sell() {
        return "sell a ticket!";
    }


}