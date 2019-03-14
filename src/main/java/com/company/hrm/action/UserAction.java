package com.company.hrm.action;

import com.company.hrm.common.ResResult;
import com.company.hrm.common.ServiceConst;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.User;
import com.company.hrm.service.iService.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserAction extends ActionSupport implements ModelDriven<User>, RequestAware, SessionAware {

    private User user = new User();

    @Override
    public User getModel() {
        return user;
    }

    public String userExist() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        IUserService userService = (IUserService) SpringIOC.getCtx().getBean("userService");
        boolean flag = userService.isExist(user.getUsername());
        ResResult result = flag ? ResResult.success() : ResResult.error(404, "no such user");
        String resultJson = new ObjectMapper().writeValueAsString(result);
        PrintWriter out = response.getWriter();
        out.println(resultJson);
        out.flush();
        out.close();
        return null;
    }

    public String userLogin() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        IUserService userService = (IUserService) SpringIOC.getCtx().getBean("userService");
        User u = userService.login(user.getUsername(), user.getUserpassword());

        ResResult<User> result = null;
        if (user != null) {
            sessionMap.put("username", user.getUsername());
            result = ResResult.success("login success", user);
        } else {
            result = ResResult.error(500, "password error");
        }
        PrintWriter out = response.getWriter();
        String jsonResult = new ObjectMapper().writeValueAsString(result);
        out.println(jsonResult);
        out.flush();
        out.close();
        return null;
    }

    public String userRegist() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        IUserService userService = (IUserService) SpringIOC.getCtx().getBean("userService");
        User u = new User(user.getUsername(),user.getUserpassword(),1);
        String a = userService.regist(user);
        ResResult<User> res = null;
        if(a.equals(ServiceConst.SUCCESS)){
            res = ResResult.success("regist success!",user);
        }else{
            res = ResResult.error(500, "regist failed!");
        }
        PrintWriter out = response.getWriter();
        String jsonResult = new ObjectMapper().writeValueAsString(res);
        out.println(jsonResult);
        out.flush();
        out.close();
        return null;
    }

    private Map<String, Object> requestMap = new HashMap<String, Object>();
    @Override
    public void setRequest(Map<String, Object> map) {
        this.requestMap = map;
    }

    private Map<String, Object> sessionMap = new HashMap<String, Object>();
    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }
}
