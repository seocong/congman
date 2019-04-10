<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cal detail</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<%
	//Object obj = requset.getAttribute("calDto");
	//CalDto calDto = (CalDto) obj
	//CalDto obj= (CalDto)request.getAttribute("calDto");	
%>
<script>
	function updateForm(seq){
		location.href="Calcontroller.do?command=calupdateform&seq="+seq;
	}
	function delBoard(seq){
		location.href="Calcontroller.do?command=muldel&chk="+seq;
	}
	function calendar(){
		location.href="Calcontroller.do?command=calendar";
	}
</script>
<body>
<jsp:useBean id="util" class="com.hk.util.Util"></jsp:useBean>
	<h1>일정상세보기</h1>
	<table border="1">
		<tr>
			<th>아이디</th>
			<td>${calDto.id}</td>
		</tr>
		<tr>
			<th>일정</th>
			<td>
				<jsp:setProperty property="toDates" name="util" value="${calDto.mdate }"/>
				<jsp:getProperty property="toDates" name="util"/>			
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${calDto.title}</td>
		</tr>
		<tr>
			<th>일정내용</th>
			<td><textarea rows="10" cols="60" readonly="readonly">${calDto.content}</textarea> </td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="수정" onclick='updateForm(${calDto.seq})' ></td>
			<td><input type="button" value="삭제" onclick="delBoard(${calDto.seq})"></td>
			<td><input type="button" value="달력" onclick="calendar()"></td>
		</tr>
	</table>
</body>
</html>