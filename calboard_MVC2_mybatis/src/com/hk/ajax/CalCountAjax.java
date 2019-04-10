package com.hk.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.daos.CalDao;

/**
 * Servlet implementation class CalCountAjax
 */
@WebServlet("/CalCountAjax.do")
public class CalCountAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//회원관리 테이블이 있으면 세션에 회원정보를 구해서 ID를 구하면 된다.
		//LoginDto ldto=(LoginDto) rquest.getSession().getAttribute("ldto");
		String id="shm";
		String yyyyMMdd=request.getParameter("yyyyMMdd");
		
		CalDao dao=new CalDao();
		int count = dao.getCalViewCount(id, yyyyMMdd);
		
		PrintWriter pw = response.getWriter();
		pw.println(count+"");
	}

}
