package com.hk.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.hk.dtos.CalDto;

public class Util {
	//uesbean을 사용하기 위한 구조를 만든다: dto처럼 멤버필드가 있고, get/set 메서드가 있는 구조
	private String toDates;//날짜를 나타내는 문자열(mdate)을 날짜형식으로 만들어서 저장할 변수

	public static String isTwo(String s) {
		return s.length()<2?"0"+s:s;
	}


	//화면에서 getproperty 태그가 호출해줄 메서드
	public String getToDates() {
		return toDates;
	}
	//화면에서 setproperty 태그가 호출해줄 메서드
	public void setToDates(String mDate) {//mDate --> 화면으로부터 12자리 문자열을 받게될 파라미터
		//date 형식: yyyy-MM-dd hh:mm:ss --> 날짜형식으로 변환하려면 date형식으로 모양을 바꿔줘야함.
		//Timestamp에 들어갈 문자열은 date형식의 모양을 갖추지 않으면 formatException 발생
		System.out.println("mDate확인: "+mDate);
		String m = mDate.substring(0, 4)+"-"+mDate.substring(4,6)+"-"
				+mDate.substring(6,8)+" "
				+mDate.substring(8,10)+":"
				+mDate.substring(10)+":00";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일 hh시 mm분");
		//java.sql 패키지에 있는거 사용
		//**중요**
		System.out.println(m);
		Timestamp tm = Timestamp.valueOf(m);
		//문자열을 date타입으로 변환을 해야 사용가능.
		this.toDates = sdf.format(tm);
	}

	public static String fontColor(int dayOfWeek, int i) {
		String color="";
		if ((dayOfWeek - 1 + i) % 7 == 0) {
			color="blue"; 
		}else if ((dayOfWeek - 1 + i) % 7 == 1){
			color="red";
		}else{
			color="black";
		}
		return color;
	}
	
	public static String getCalView(List<CalDto> list, int i) {
		//list는 mdate에 ex) "201903011530"
		//---->mdate에서 "01"추출 == i에서 int형 1-->"01"
		String d = isTwo(i+"");//숫자1을 문자열 "01" 바꿔줌
		String calList="";//화면에 출력해줄 문자열(결과값)
		for (CalDto calDto : list) {
			if(calDto.getMdate().substring(6, 8).equals(d)) {
				calList+="<p title='"+calDto.getTitle()+"' class='clist' style='font-size:8px; background-color: gray;'>"+ (calDto.getTitle().length()>6?calDto.getTitle().substring(0, 5)+"..":calDto.getTitle())
						+ "</p>";
			}
		}
		return calList;
	}
}
