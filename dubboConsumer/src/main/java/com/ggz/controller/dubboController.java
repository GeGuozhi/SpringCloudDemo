package com.ggz.controller;

import com.ggz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class dubboController {

    final
    UserService userService;

    public dubboController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/dubboTest")
    public String dubboTest() {
        return userService.sellTicket();
    }
}
