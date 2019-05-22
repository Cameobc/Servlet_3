<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setAttribute("s", 5);
	request.setAttribute("e", 10);
	String [] ar = {"a", "b", "c"};
	request.setAttribute("ar", ar);
	ArrayList<Integer> list = new ArrayList<Integer>();
	list.add(100);
	list.add(200);
	list.add(300);
	request.setAttribute("list", list);
%>

<c:forEach begin="${s}" end="${e}" var="i">
	${pageScope.i}
</c:forEach>
<c:forEach items="${ar}" var="a">
${a}
</c:forEach>
<c:forEach items="${list }" var="li" varStatus="t">
<p>${li}</p>
<h3>Count : ${t.count }</h3>
<h3>Index : ${t.index }</h3>
<h3>First : ${t.first}</h3>
<h3>Last : ${t.last }</h3>
</c:forEach>
</body>
</html>