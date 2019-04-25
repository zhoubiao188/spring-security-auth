package cn.com.scitc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping(value = "/user/login")
    private String loginPage(){
        return "login";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/admin/index")
    public String adminPage(){
        return "admin/admin";
    }

    @GetMapping(value = "/person")
    public String personPage(){
        return "person";
    }

    @GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

    @GetMapping("/success")
    @ResponseBody
    public String success(){
        return "认证成功,进入success成功";
    }
}
