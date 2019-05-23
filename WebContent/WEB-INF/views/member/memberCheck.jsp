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
      <label><input type="checkbox" id="checkAll">모두동의</label>
    </div>
    <div class="checkbox">
      <label><input type="checkbox" class="check">A</label>
    </div>
    <div class="checkbox">
      <label><input type="checkbox" class="check">B</label>
    </div>
     <div class="checkbox">
      <label><input type="checkbox" class="check" >C</label>
    </div>
    <input type="button" value="Next" class="btn btn-primary">
  </form>
</div>
<script type="text/javascript">
//*********************************************모두동의하는부분
	$('#checkAll').click(function() {
		var c = $('#checkAll').prop('checked');
		$('.check').prop('checked', c);
	});
//*********************************************박스 하나라도 체크 안하면 안 넘어가게
	$('.check').click(function() {
		var c = true;
		$('.check').each(function() {
			if(!$(this).prop('checked')){
				c=false;
			}
		});
		$('#checkAll').prop('checked', c);
	});
//******************************************전체 체크해야지 a태그 돌아가도록
	$('.btn').click(function() {
		if(!$('#checkAll').prop('checked')){
			alert("약관에 모두 동의해주세요.");
		}else{
			location.href="./memberJoin";
		}
	});
</script>
</body>
</html>