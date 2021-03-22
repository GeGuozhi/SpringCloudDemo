package com.ggz.controller;

import com.ggz.entity.TestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oauth2ServerController extends JpaRepository<TestEntity,Long>{

    @GetMapping("addEntity")
    public void addEntity(TestEntity testEntity){

    }
}
