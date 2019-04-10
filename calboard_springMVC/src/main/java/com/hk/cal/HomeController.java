package com.hk.cal;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hk.cal.service.ICalService;
import com.hk.dtos.CalDto;
import com.hk.util.Util;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ICalService calService;
	private String id = "shm";
	
	@RequestMapping(value="calendar.do", method=RequestMethod.GET)
	public String calendar(Locale locale, Model model,String year,String month) {
		if(year==null) {
		Calendar cal = Calendar.getInstance();//calendar 객체는 new를 사용하지 않는다.
		year = ""+cal.get(Calendar.YEAR);//현재 년도를 구함
		month =""+ (cal.get(Calendar.MONTH) + 1);//Calendar(0월~11월을 구함, 12개)
		}
		String yyyyMM = year+Util.isTwo(month);
		System.out.println(yyyyMM);
		List<CalDto> list = calService.getCalViewList(id, yyyyMM);
		model.addAttribute("list", list);
		return "calendar";
	}
	@ResponseBody
	@RequestMapping(value="CalCountAjax.do", method=RequestMethod.POST)
	public int calcount(Locale locale, Model model, String yyyyMMdd) {
		int count = calService.getCalViewCount(id, yyyyMMdd);
		System.out.println("count:"+count);
		return count;
	}
	@RequestMapping(value="callist.do", method=RequestMethod.GET)
	public String callist(Locale locale, Model model, String year, String month, String date) {
		String yyyyMMdd = year+Util.isTwo(month)+Util.isTwo(date);
		List<CalDto> list = calService.getCalList(id, yyyyMMdd);
		model.addAttribute("list",list);
		return "callist";
	}
	@RequestMapping(value="calinsertform.do", method=RequestMethod.GET)
	public String calinsertform(Locale locale, Model model) {
		return "insertCal";
	}
	@RequestMapping(value="calinsert.do", method=RequestMethod.POST)
	public String calinsert(HttpServletRequest request, HttpServletResponse response, Locale locale, Model model,String year, String month, String date, String hour, String min, String title, String content) {
		String yyyyMMddhhmm=year+Util.isTwo(month)+Util.isTwo(date)+Util.isTwo(hour)+Util.isTwo(min);
		CalDto dto = new CalDto().setMdate(yyyyMMddhhmm).setId(id).setContent(content).setTitle(title);
		boolean isS = calService.insertCalboard(dto);
		if(isS) {
			return "redirect:calendar.do";
		}else {
			model.addAttribute("msg","글추가 실패");
			return "error";
		}
		
	}
	@RequestMapping(value="caldetail.do", method=RequestMethod.GET)
	public String caldetail(Locale locale, Model model,int seq) {
		CalDto dto = calService.getCalBoard(seq);
		model.addAttribute("calDto",dto);
		return "caldetail";
	}
	@RequestMapping(value="muldel.do", method= {RequestMethod.POST, RequestMethod.GET})
	public String muldel(Locale locale, Model model, String[] chk) {
		boolean isS=calService.calMulDel(chk);
		if(isS) {
			return "redirect:calendar.do";
		}else {
			model.addAttribute("msg","삭제 실패");
			return "error";
		}
		
	}
	@RequestMapping(value="calupdateform.do", method=RequestMethod.GET)
	public String calupdateform(Locale locale, Model model) {
		return "calupdate";
	}
	@RequestMapping(value="calupdate.do", method=RequestMethod.GET)
	public String calupdate(Locale locale, Model model) {
		return "calendar";
	}
}
