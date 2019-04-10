package com.hk.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.daos.CalDao;
import com.hk.dtos.CalDto;
import com.hk.util.Util;

/**
 * Servlet implementation class CalController
 */
@WebServlet("/Calcontroller.do")
public class CalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command=request.getParameter("command");
		CalDao dao=new CalDao();
		
		if(command.equals("calendar")) {
			response.sendRedirect("calendar.jsp");
		}else if(command.equals("callist")) {
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String date = request.getParameter("date");
			request.getSession().setAttribute("year", year);
			request.getSession().setAttribute("month", month);
			request.getSession().setAttribute("date", date);
			//id값 임의로 가져옴
			String id = "shm";
			String yyyyMMdd=year+Util.isTwo(month)+Util.isTwo(date);
			
			List<CalDto> list = dao.getCalList(id, yyyyMMdd);
			request.setAttribute("list", list);
			dispatch("callist.jsp",request,response);
			
		}else if(command.equals("calinsertform")) {
			dispatch("insertCal.jsp",request,response);
		}else if(command.equals("calinsert")) {
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String date = request.getParameter("date");
			String min = request.getParameter("min");
			String hour = request.getParameter("hour");
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			//id값 임의로 가져옴
			String id = "shm";
			String yyyyMMddhhmm=year+Util.isTwo(month)+Util.isTwo(date)+Util.isTwo(hour)+Util.isTwo(min);
			
			CalDto calDto = new CalDto();
			calDto.setMdate(yyyyMMddhhmm);
			calDto.setId(id);
			calDto.setTitle(title);
			calDto.setContent(content);
			boolean isS = dao.insertCalboard(calDto);
			if(isS) {
				response.sendRedirect("Calcontroller.do?command=calendar");
			}else {
				request.setAttribute("msg", "일정추가 실패");
				dispatch("error.jsp",request,response);
			}
		//디테일보드
		}else if(command.equals("caldetail")) {
			int seq=Integer.parseInt(request.getParameter("seq"));
			CalDto calDto = dao.getCalBoard(seq);
			request.setAttribute("calDto", calDto);
			dispatch("caldetail.jsp",request,response);
		//삭제하기
		}else if(command.equals("muldel")) {
			String[] seqs = request.getParameterValues("chk");
			boolean isS=dao.calMulDel(seqs);
			if(isS) {
				//일정목록으로 이동하기위해 필요한 파라미터 year, month, date를 세션에서 구한다.
				String year = (String) (request.getSession().getAttribute("year"));
				String month = (String) (request.getSession().getAttribute("month"));
				String date = (String) (request.getSession().getAttribute("date"));
				System.out.println("year="+year);
				System.out.println("month="+month);
				System.out.println("date="+date);
				String a = "&year="+year+"&month="+month+"&date="+date;
				response.sendRedirect("Calcontroller.do?command=callist"+a);
			}else {
				request.setAttribute("msg", "글삭제실패");
				dispatch("error.jsp",request,response);
			}
		}else if(command.equals("calupdateform")) {
			int seq = Integer.parseInt(request.getParameter("seq"));
			CalDto dto = dao.getCalBoard(seq);
			request.setAttribute("dto",dto);
			dispatch("calupdate.jsp",request,response);
		}else if(command.equals("calupdate")) {
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String date = request.getParameter("date");
			String min = request.getParameter("min");
			String hour = request.getParameter("hour");
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			//id값 임의로 가져옴
			String yyyyMMddhhmm=year+Util.isTwo(month)+Util.isTwo(date)+Util.isTwo(hour)+Util.isTwo(min);
			int seq = Integer.parseInt(request.getParameter("seq"));
			CalDto calDto = new CalDto();
			calDto.setSeq(seq);
			calDto.setMdate(yyyyMMddhhmm);
			calDto.setTitle(title);
			calDto.setContent(content);
			boolean isS = dao.updateCalBoard(calDto);
			if(isS) {
				response.sendRedirect("Calcontroller.do?command=caldetail&seq="+seq);
			}else {
				request.setAttribute("msg", "일정추가 실패");
				dispatch("error.jsp",request,response);
			}
			
		}
	}
	
	public void dispatch(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(url);
		dispatch.forward(request,response);
	}
}
