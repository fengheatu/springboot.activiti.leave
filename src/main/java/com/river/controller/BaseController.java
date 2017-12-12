package com.river.controller;

import com.alibaba.fastjson.JSONObject;
import com.river.constant.UserConst;
import com.river.model.po.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: he.feng
 * @date: 16:36 2017/12/8
 * @desc:
 **/
public class BaseController {

    public User getCurrentUser(HttpServletRequest request) {
      User user = (User) request.getSession().getAttribute(UserConst.USER_SESSION_SING);
      if(null == user) {
          throw new RuntimeException("no login");
      }
      return user;
    }

    public String objectToJson(Object object) {
        return JSONObject.toJSONString(object);
    }
}