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
<h1>약관 동의</h1>
  <form>
    <div class="checkbox">
      <label><input type="checkbox" value="" name="">모두동의</label>
    </div>
    <div class="checkbox">
      <label><input type="checkbox" value="">A</label>
    </div>
    <div class="checkbox">
      <label><input type="checkbox" value="">B</label>
    </div>
     <div class="checkbox">
      <label><input type="checkbox" value="" >C</label>
    </div>
    <a href=""><input type="button" value="Next" class="btn btn-primary"></a>
  </form>
</div>
</body>
</html>