package com.ggz.Controller;

import com.google.common.collect.Maps;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.protobuf.compiler.PluginProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class TestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GitProperties git;

    public TestController(GitProperties git) {
        this.git = git;
    }

    @PostMapping("search")
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
        if("success".equals(password)){
            modelAndView.setViewName("show");
        }else{
            modelAndView.setViewName("error");
        }
        Map<String, String> model = Maps.newConcurrentMap();
        model.put("username",git.getShortCommitId());
        modelAndView.getModelMap().addAllAttributes(model);
        return modelAndView;

    }

    public static void main(String[] args) {

        String a = "aaa";
        switch (a){
            case "bbb":
                System.out.println("bbb");
            case "aaa":
                System.out.println("aaa");
            case "ccc":
                System.out.println("ccc");
        }
    }

}
