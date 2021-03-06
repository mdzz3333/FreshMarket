package com.greengrocer.freshmarket.web.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.greengrocer.freshmarket.domain.Admin;
import com.greengrocer.freshmarket.exception.UserException;
import com.greengrocer.freshmarket.service.AdminService;
import com.greengrocer.freshmarket.utils.WebUtils;
import com.greengrocer.freshmarket.web.formbean.UpdatePasswordForm;



public class AdminServlet extends BaseServlet {


	/**
	 * 管理员登陆
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AdminService service = new AdminService();
	
		//封装表单数据到Admin form中
		Admin form = WebUtils.request2Bean(request, Admin.class);
		try {
			//调用service的login()方法，得到返回的Admin admin对象。
			Admin admin = service.login(form);
			//如果没有异常：保存返回值到session中，重定向到首页
			request.getSession().setAttribute("sessionUser", admin);
			//重定向到首页
			//response.sendRedirect(request.getContextPath() + "/index.jsp");
			return "r:/adminjsps/admin/main.jsp";
			
		} catch (UserException e) {
			//如果抛出异常：获取异常信息，保存到request域，再保存form，转发到adminLogin.jsp
			request.setAttribute("errorMessage", e.getMessage());
			//存入数据，回显给login.jsp页面
			request.setAttribute("admin", form);
			//跳转
			//request.getRequestDispatcher("/adminLogin.jsp").forward(request, response);
			return "/adminLogin.jsp";
		}
		
		
	}
	
	
	/**
	 * 用户注销
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.invalidate();
		}
		//注销成功重定向到转到首页
		//response.sendRedirect(request.getContextPath()+"/adminLogin.jsp");
		return "r:/adminLogin.jsp";

	}
	
	
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//封装表单数据到AdminForm中
		UpdatePasswordForm adminForm = WebUtils.request2Bean(request, UpdatePasswordForm.class);
		//错误校验
		boolean b = adminForm.validate();
		if(!b){
			//显示错误消息
			request.setAttribute("errors", adminForm.getErrors());
			//回显数据
			request.setAttribute("form", adminForm);
			//跳转会修改页面
			return "/adminjsps/admin/changePassword.jsp";
		}
		AdminService service = new AdminService();
		try {
			service.changePassword(adminForm);
		} catch (UserException e) {
			adminForm.getErrors().put("oldpassword", e.getMessage());
			//显示错误消息
			request.setAttribute("errors", adminForm.getErrors());
			//回显数据
			request.setAttribute("form", adminForm);
			//跳转会修改页面
			return "/adminjsps/admin/changePassword.jsp";
		}
		//程序到这里表示修改成功，显示成功信息
		request.setAttribute("message", "修改成功");
		return "/message.jsp";
		
	}
	
	

}
