package com.river.controller.user;

import com.river.constant.UserConst;
import com.river.model.po.Role;
import com.river.model.po.User;
import com.river.service.RoleService;
import com.river.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * @author: he.feng
 * @date: 10:17 2017/12/11
 * @desc:
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(User user,ModelAndView modelAndView,HttpSession session) {
        User currentUser = userService.queryUserByMobile(user.getMobile());
        if (null == currentUser) {
            throw new RuntimeException("用户不存在");
        }
        List<Role> roleList = roleService.queryRoleByUserId(currentUser.getId());
        session.setAttribute(UserConst.USER_SESSION_SING,currentUser);
        Role role = null;
        if(null != roleList && roleList.size() > 0) {
            role = roleList.get(0);
        }
        session.setAttribute("role",role);
        modelAndView.setViewName("main/index");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView,HttpServletRequest request) {
        request.getSession().removeAttribute(UserConst.USER_SESSION_SING);
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
