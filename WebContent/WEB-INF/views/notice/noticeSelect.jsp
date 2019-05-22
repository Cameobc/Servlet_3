<%@page import="com.iu.notice.NoticeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="../temp/bootstrap.jsp" %>
</head>
<body>
<jsp:include page="../temp/header.jsp"></jsp:include>
<div class="container">
<h1>NoticeSelect Page</h1>
	<table class="table table-hover">
		<tr>
			<td>NO</td><td>TITLE</td><td>WRITE</td><td>DATE</td><td>HIT</td>
		</tr>
		<tr>
			<td>${select.no}</td><td>${select.title} </td><td>${select.writer}</td><td>${select.reg_date}</td><td>${select.hit}</td>
		</tr>
		<tr>
		<td colspan="5">${select.contents}</td>
		</tr>
	</table>
	<H1>Param : ${param.no le 1} </H1>
	<h1>Title : ${select.title ne '옥탑방' }</h1>
</div> 
<a href="./noticeUpdate?no=${select.no}">Go Update</a>
<a href="./noticeDelete?no=${select.no}">Go Delete</a>
</body>
</html>