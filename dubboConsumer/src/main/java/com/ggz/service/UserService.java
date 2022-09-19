package com.ggz.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Reference
    private TicketService service;

    public String sellTicket() {
        return service.sell();
    }
}