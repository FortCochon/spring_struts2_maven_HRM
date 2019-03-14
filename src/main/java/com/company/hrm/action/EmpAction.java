package com.company.hrm.action;

import com.company.hrm.common.ResResult;
import com.company.hrm.common.ServiceConst;
import com.company.hrm.common.SpringIOC;
import com.company.hrm.dao.pojo.Emp;
import com.company.hrm.dao.pojo.User;
import com.company.hrm.service.iService.IEmpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmpAction extends ActionSupport implements ModelDriven<Emp>, RequestAware, SessionAware {
    private Emp emp = new Emp();

    @Override
    public Emp getModel() {
        return emp;
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

    public String empDelete() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        int empno = Integer.parseInt((String) requestMap.get("empno"));
        Emp emp = new Emp();
        emp.setEmpno(empno);
        IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
        String msg = empservice.delete(emp);
        ResResult<Emp> res = null;
        if (msg.indexOf(ServiceConst.SUCCESS) != -1) {
            res = ResResult.success("delete success!");
        } else {
            res = ResResult.error(500, "delete error!");
        }
        PrintWriter out = response.getWriter();
        String jsonResult = new ObjectMapper().writeValueAsString(res);
        out.println(jsonResult);
        out.flush();
        out.close();
        return null;
    }

    public String empFindAll() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        ResResult<List<Emp>> result = null;
        if (sessionMap.get("username") != null) {
            IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
            List<Emp> empList = new ArrayList<Emp>();
            empList = empservice.findAll();
            if (!empList.isEmpty() && empList.size() > 0) {
                result = ResResult.success("find all success", empList);
            } else {
                result = ResResult.error(404, "no data");
            }
        } else {
            result = ResResult.error(301, "have not login");

        }
        PrintWriter out = response.getWriter();
        String jsonResult = new ObjectMapper().writeValueAsString(result);
        out.println(jsonResult);
        out.flush();
        out.close();
        return null;
    }

    public String empFindById() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        int empno = Integer.parseInt((String) requestMap.get("empno"));
        IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
        Emp emp = null;
        ResResult<List<Emp>> res = null;
        List<Emp> empList = new ArrayList<Emp>();
        if (empservice.findById(empno) != null) {
            emp = empservice.findById(empno);
            empList.add(emp);
            res = ResResult.success("find success", empList);
        } else {
            res = ResResult.error(404, "no data");
        }
        PrintWriter out = response.getWriter();
        String jsonResult = new ObjectMapper().writeValueAsString(res);
        System.out.println(jsonResult);
        out.println(jsonResult);
        out.flush();
        out.close();
        return null;
    }

    public String empFindByName() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        String ename = (String) requestMap.get("ename");
        IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
        ResResult<List<Emp>> res = null;
        List<Emp> empList = new ArrayList<Emp>();
        if (empservice.findByName(ename) != null) {
            empList = empservice.findByName(ename);
            res = ResResult.success("find success", empList);
        } else {
            res = ResResult.error(404, "no data");
        }
        PrintWriter out = response.getWriter();
        String jsonResult = new ObjectMapper().writeValueAsString(res);
        System.out.println(jsonResult);
        out.println(jsonResult);
        out.flush();
        out.close();
        return null;
    }

    public String empSave() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
        Emp e = new Emp(emp.getEmpno(),emp.getEname(),emp.getJob(),emp.getMgr(),emp.getHiredate(),emp.getSal(),emp.getComm(),emp.getDeptno());
        String msg = empservice.save(e);
        ResResult<Emp> res = null;
        if (msg.indexOf(ServiceConst.SUCCESS) != -1) {
            res = ResResult.success("save success!");
        }else {
            res = ResResult.error(500, "sava error!");
        }
        PrintWriter out = response.getWriter();
        String jsonObj = new ObjectMapper().writeValueAsString(res);
        out.println(jsonObj);
        out.flush();
        out.close();
        return null;
    }

    public String empUpdate() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        IEmpService empservice = (IEmpService) SpringIOC.getCtx().getBean("empService");
        Emp e = new Emp(emp.getEmpno(),emp.getEname(),emp.getJob(),emp.getMgr(),emp.getHiredate(),emp.getSal(),emp.getComm(),emp.getDeptno());
        String msg = empservice.update(e);
        ResResult<Emp> res = null;
        if (msg.indexOf(ServiceConst.SUCCESS) != -1) {
            res = ResResult.success("update success!");
        }else {
            res = ResResult.error(500, "update error!");
        }
        PrintWriter out = response.getWriter();
        String jsonObj = new ObjectMapper().writeValueAsString(res);
        out.println(jsonObj);
        out.flush();
        out.close();
        return null;
    }
}
