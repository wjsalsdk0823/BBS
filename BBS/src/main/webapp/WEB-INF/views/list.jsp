<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물</title>
</head>
<style>
table {
border-collapse:collapse;
}
td {
	color:white;
	background-color:green;
	border:1px solid yel10;
}
</style>
<body>
<table align="center" valign=top>
<thead>
	<tr><th>게시물번호</th><th>제목</th><th>작성자</th><th>작성시간</th><th>수정시각</th></tr>
</thead>
<c:forEach items="${list}" var="bbs">
	<tr><td>${bbs.bbs_id}</td><td>${bbs.title}</td><td>${bbs.writer}</td>
	<td>${bbs.created}</td><td>${bbs.updated}</td></tr>
</c:forEach>		
</table>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>//아래 제이쿼리
$(document)
.on('click','tr',function(){
	let bbs_id=$(this).find('td:eq(0)').text();//this는 윗줄tr을 의미한다//find제일 왼쪽의 
	console.log('bbs_id ['+bbs_id+']');
	document.location="/app/view/"+bbs_id;//다큐멘티로케이션 서버로 날아간다?
	return false;
})
</script>
</body>
</html>