<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript">
	$(function() {
			var count=0;
			var d1=0;
		$('#up').click(function() {
			d1++;
				if(count<5){
					$('#addfile').append('<input type="file" class="form-control" name="f'+d1+'"><span class="del">X</span>');
					/*<div><input type="file" class="form-control" name="f1"><span class="del">X</span></div> ->this.parent().remove(); 세번째는 jquery5_html 보기*/
					count++;
				}else{
					alert('5개까지만 추가 가능');
				}
		});
		
 		$('#addfile').on("click", ".del", function() {
			$(this).prev().remove();
			$(this).remove();
			count--;
		});
		
	});
</script>
<style type="text/css">
	.del{
		color : red;
		cursor: pointer;
	}
</style>
</head>
<body>
<c:import url="../temp/header.jsp"/>
<div class="container">
	<h1>QnA Write</h1>
	<form action="./qnaWrite" method="post" enctype="multipart/form-data">
	    <div class="form-group">
	      <label for="title">Title:</label>
	      <input type="text" class="form-control" id="title" name="title">
	    </div>
	    <div class="form-group">
	      <label for="writer">Writer:</label>
	      <input type="text" class="form-control" id="writer" name="writer">
	    </div>
	    <div class="form-group">
		  <label for="contents">Contents:</label>
		  <textarea class="form-control" rows="20" id="contents" name="contents"></textarea>
		</div>
		<div class="form-group">
	      <label for="file" id="addfile">File:</label>
	    </div>
	    <div class="form-group">
		    <input type="button" value="Add" id="up" class="btn btn-primary">
	    </div>
		<button class="btn btn-danger">Write</button>
  </form>
</div>
</body>
</html>