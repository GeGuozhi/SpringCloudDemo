package com.ggz.controller;

import com.ggz.entity.TestEntity;
import com.ggz.entity.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//@RestController
@Controller
public class Oauth2ServerController{

    @Autowired
    private TestEntityFactory testEntityFactory;

    @ResponseBody
    @GetMapping("addEntity")
    public String addEntity(TestEntity testEntity, HttpServletRequest request){
        testEntityFactory.save(testEntity);
        return "保存成功了";
    }
}
