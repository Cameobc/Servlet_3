<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
</head>
<body>
<c:import url="../temp/header.jsp"/>
<div class="container">
<h1>ID : ${memberDTO.id }</h1>
<h1>NAME : ${memberDTO.name }</h1>
<h1>AGE : ${memberDTO.age }</h1>
<h1>PHONE : ${memberDTO.phone }</h1>
<h1>EMAIL : ${memberDTO.email }</h1>
<h1>PHOTO : ${memberDTO.muploadDTO.fname }</h1>
</div>
</body>
</html>