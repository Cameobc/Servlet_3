<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../temp/bootstrap.jsp"></jsp:include>
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	alert('${message}');
	location.href="${path}";
</script>
</body>
</html>