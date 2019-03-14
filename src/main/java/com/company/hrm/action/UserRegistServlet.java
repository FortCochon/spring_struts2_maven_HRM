package com.company.hrm.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.hrm.common.ResResult;
import com.company.hrm.common.ServiceConst;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.User;
import com.company.hrm.service.iService.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = {"/UserRegistServlet"})
public class UserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		IUserService userService = (IUserService) SpringIOC.getCtx().getBean("userService");
		User user = new User(username,password1,1);
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
		
				
	}

}
