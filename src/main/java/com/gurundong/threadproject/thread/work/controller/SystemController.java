package com.gurundong.threadproject.thread.work.controller;

import com.gurundong.threadproject.thread.work.service.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/bss/system/")
public class SystemController {
    private final SystemService systemService;

    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/test01")
    public String initSuperAdminForPublicBss(String userId){
        return systemService.initSuperAdminForPublicBss(userId);
    }
}
