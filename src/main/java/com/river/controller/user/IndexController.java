package com.river.controller.user;

import com.river.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: he.feng
 * @date: 16:35 2017/12/8
 * @desc:
 **/
@Controller
public class IndexController extends BaseController{

    @RequestMapping("/")
    public String index() {
        return "login";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "main/welcome";
    }

    @RequestMapping("/main/index")
    public String main() {
        return "/main/index";
    }

}
