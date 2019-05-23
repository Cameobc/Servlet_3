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
	<form class="form-inline" action="./memberLogin">
  <div class="form-group">
    <label for="id">ID:</label>
    <input type="text" class="form-control" id="id" value="${cookie.c.value }" name="id">
  </div>
  <div class="form-group">
    <label for="pw">Password:</label>
    <input type="password" class="form-control" id="pw" name="pw">
  </div>
  <div class="checkbox">
    <label><input type="checkbox" name="remember"> Remember me</label>
  </div>
  <button type="submit" class="btn btn-default">Login</button>
</form>
</div>
</body>
</html>