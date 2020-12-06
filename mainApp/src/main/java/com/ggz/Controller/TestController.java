package com.ggz.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("search")
    public ModelAndView show(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("show","html");
        return  modelAndView;
    }

    @RequestMapping("test")
    public String test(){
        return "index";
    }

    @RequestMapping("login")
    public String login(){
        return "show";
    }
}
