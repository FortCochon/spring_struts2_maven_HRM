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
import com.company.hrm.dao.pojo.Emp;
import com.company.hrm.service.iService.IEmpService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = {"/EmpDeleteServlet"})
public class EmpDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int empno = Integer.parseInt(request.getParameter("empno"));
		Emp emp = new Emp();
		emp.setEmpno(empno);
		IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
		String msg = empservice.delete(emp);
		ResResult<Emp> res = null;
		if (msg.indexOf(ServiceConst.SUCCESS) != -1) {
			res = ResResult.success("delete success��");
		}else {
			res = ResResult.error(500, "delete error!");
		}
		PrintWriter out = response.getWriter();
		String jsonResult = new ObjectMapper().writeValueAsString(res);
		out.println(jsonResult);
		out.flush();
		out.close();
	}

}
