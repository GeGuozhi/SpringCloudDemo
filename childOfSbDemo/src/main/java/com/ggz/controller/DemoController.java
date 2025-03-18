package com.ggz.controller;

import com.ggz.customizeAnnotation.ActionLog;
import com.ggz.service.TestProfileService;
import com.ggz.service.UserService;
import com.ggz.service.UserService2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/serviceNumberOne")
public class DemoController {
    private final UserService userService;

    private final UserService2 userService2;

    private final TestProfileService testProfileService;

    public DemoController(UserService userService, UserService2 userService2, TestProfileService testProfileService) {
        this.userService = userService;
        this.userService2 = userService2;
        this.testProfileService = testProfileService;
    }
//    private UserServiceImpl2 userServiceImpl2;

    @GetMapping("test")
    public String test() throws Exception {
        System.out.println("111");
        userService.select("abc");
        userService.update("abc");
//        userServiceImpl2.select();
        return "test 返回";
    }

    @RequestMapping("post")
    @ActionLog(module = "支付模块", action = "支付接口", error = "操作失败")
    public String post() {
        return "post";
    }

    @RequestMapping(value = "/saveSecuInst.action")
    @ResponseBody
    public Object saveSecuInst(HttpServletRequest request) {

        Map<String, String[]> map = request.getParameterMap();

        Map<String, Object> ret = new HashMap<String, Object>();

        for (String key : map.keySet()) {

            ret.put(key, map.get(key)[0]);
        }
        Double a = (Double.valueOf((String) ret.get("REAL_CP_BALANCE")));
        return null;
    }


    @GetMapping("/profile")
    public String profileTest() {
        return testProfileService.print();
    }

    @GetMapping("updateUser")
    public String updateUser() throws Exception {
        userService.update("1");
        return "sucdess";
    }
}
