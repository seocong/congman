<%@page import="com.hk.dtos.CalDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.daos.CalDao"%>
<%@page import="com.hk.util.Util"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>달력보기</title>
</head>
<%
	//달력의 날짜 변경을 위한 전달된 파라미터 값(year, month)
	String pYear = request.getParameter("year");
	String pMonth = request.getParameter("month");

	Calendar cal = Calendar.getInstance();//calendar 객체는 new를 사용하지 않는다.

	int year = cal.get(Calendar.YEAR);//현재 년도를 구함
	int month = cal.get(Calendar.MONTH) + 1;//Calendar(0월~11월을 구함, 12개)

	if (pYear != null) {
		year = Integer.parseInt(pYear);
	}
	if (pMonth != null) {
		month = Integer.parseInt(pMonth);
	}

	//달중에 12월을 넘어갔을 경우 month는 1월로, 년도는 다음년도로 값을 바꿔준다.
	if (month > 12) {
		month = 1;
		year++;
	}
	//달중에 1월을 넘어갔을 경우 month는 12월로, 년도는 이전년도로 값을 바꿔준다.
	if (month < 1) {
		month = 12;
		year--;
	}
	//현재 월의 1일에 대한 요일 구하기 --> 현재 날짜에 대한 정보를 담고 있는 Calendar객체 생성
	//						 --> 3월 1일로 셋팅을 해서 요일을 구해야함
	cal.set(year, month - 1, 1);//cal 객체에 2019년 3월 1일 정보 입력
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//1일에 대한 요일 구함: 1(일)~7(토)

	//월의 마지막날 구하기
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	cal.set(year, month-1, lastDay);
	int lastDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	
	//한달에 대한 일정 목록 가져오기
	CalDao calDao=new CalDao();
	String id = "shm";
	String yyyyMM = year+Util.isTwo(String.valueOf(month));
	List<CalDto> list = calDao.getCalViewList(id, yyyyMM);
%>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<style type="text/css">
table td {
	position:relative;
	height: 80px;
	vertical-align: top;
	font-weight: bold;
}

table {
	border: 1px solid gray;
	border-collapse: collapse;
}

a {
	text-decoration: none;
}
.clist{background-color: orange;}
.count{color:orange; font-weight: bold;}
.otherDate {
	color: #BDBDBD;
}
.cPreview{
	position:absolute;
	left:10px;
	top:-30px;
	background-color: pink;
	width:40px;
	height:40px;
	border-radius: 40px 40px 40px 1px;
	text-align: center;
	line-height: 40px;
}
</style>
<script type="text/javascript">
	$(function(){
		$(".countview").hover(function(){
			//마우스가 올라간 태그를 찾기 위해 this사용
			var aCountView = $(this);
			//var year=$("caption>span").eq(0).text().trim();
			var year=$(".y").text().trim();//trim()문자열의 앞뒤 공백을 제거해준다.
			var month=$(".m").text().trim();
			var date=aCountView.text().trim();
			var yyyyMMdd=(year + isTwo(month) + isTwo(date));
			$.ajax({
				method:"post",
				url:"CalCountAjax.do",
				data:{"yyyyMMdd":yyyyMMdd},
				success:function(obj){//obj변수가 전달된 값을 받는다.
					//alert(obj);
					//after() 해당 객체 다음에 추가
					//append(),prepend(): 자식요소로 추가
					//after(),before(): 형제 레벨에서 다음이나 앞에 추가
					aCountView.after("<div class='cPreview'>"+obj+"</div>");
				},
				error:function(){
					alert("서버통신실패!!");
				}
			});//json type {"key":"value","key":"value"}
		},function(){
			//remove() element삭제
			$(".cPreview").remove();
		});
	});
	function isTwo(n){
		return n.length<2?"0"+n:n;
	}
</script>
<body>

	<h1>달력(일정)보기</h1>
	<table border="1">
		<caption>
			<a href="calendar.jsp?year=<%=year - 1%>&month=<%=month%>">◁</a> <a
				href="calendar.jsp?year=<%=year%>&month=<%=month - 1%>">◀</a>
				<span class="y"><%=year%></span>년<span class="m"><%=month%></span>월 
				<a href="calendar.jsp?year=<%=year%>&month=<%=month + 1%>">▶</a> 
				<a href="calendar.jsp?year=<%=year + 1%>&month=<%=month%>">▷</a>
		</caption>
		<col width="80px">
		<col width="80px">
		<col width="80px">
		<col width="80px">
		<col width="80px">
		<col width="80px">
		<col width="80px">
		<tr>
			<th>일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th>토</th>
		</tr>
		<tr>
			<%
				for (int i = 0; i < (dayOfWeek - 1); i++) {
					out.print("<td class='otherDate'>"+(i+1)+"</td>");
				}
				for (int i = 1; i <= lastDay; i++) {
			%>
			<td>
			<a class="countview" href="Calcontroller.do?command=callist&year=<%=year%>&month=<%=month%>&date=<%=i%>"
				style="color:<%=Util.fontColor(dayOfWeek,i)%>"> 
				<%-- <%if ((dayOfWeek - 1 + i) % 7 == 0) {
					out.print("blue;"); 
				}else if ((dayOfWeek - 1 + i) % 7 == 1){
					out.print("red;");
				}else{
					out.print("black;");
				}
				%>"> --%>
				 <%=i%>
							 
			</a>
				
				 <a href="Calcontroller.do?command=calinsertform&year=<%=year%>&month=<%=month%>&date=<%=i%>&lastday=<%=lastDay%>">
				 	<img src="img/iconfinder_pencil2_8672.gif" alt="일정주기" />
				 </a>
				 <%=Util.getCalView(list, i) %>
			</td>
			<%
				//dayofweek-1은 공백수: (공백수+날짜)%7==0 토요일
					if ((dayOfWeek - 1 + i) % 7 == 0) {
						out.print("</tr><tr>");
					}
				}
			%>
			<%
			
			//lastDay가 일요일(1)일 경우 6개 lastDay가 월요일(2) 5개
			//for(int i=0;i<(7-((dayOfWeek-1+lastDay)%7))%7;i++)
			for (int i = 0; i < (7-lastDayOfWeek); i++) {
				out.print("<td class='otherDate'>"+(i+1)+"</td>");
			}
			%>
		</tr>
	</table>
</body>
</html>