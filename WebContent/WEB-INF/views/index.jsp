<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="./temp/bootstrap.jsp"/>
</head>
<body>
<%@include file="./temp/header.jsp" %> <!-- 서버 내부에서 -->
<h1>Welcome ${sessionScope.memberDTO.id }</h1>
<img alt="cat" src="./images/cat1.jpg"><!-- 서버 외부에서 -->
</body>
</html>