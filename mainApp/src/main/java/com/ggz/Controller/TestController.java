package com.ggz.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


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
    public ModelAndView login(@RequestParam(value = "username",required = false) String username,
                              @RequestParam(value = "password",required = false) String password,
                              HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if("success".equals(username) && "success".equals(password)){
            modelAndView.setViewName("show");
        }else{
            modelAndView.setViewName("error");
        }
        return modelAndView;

    }

}
