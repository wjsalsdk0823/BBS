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
table#tbllist {
border-collapse:collapse;
}
table#tbllist td {
	border:1px,solid, blue;
}
/* table#tbllist tr {
	border:1px,solid, black;
} */
table#tbllist th{
	color:white;
	background-color:black;
	border:1px, solid, yellow;
}
</style>
<table align=center valign=top>
<tr><td align=right>
<c:if test="${logined eq '0'}">
<input type=button value="로그인" id=btnlogin >
</c:if>
<c:if test="${logined eq '1'}">
<input type=button value="로그아웃" id=doloout >
</c:if>
</td></tr>
	<table id=tbllist>
	<thead>
		<tr><th>게시물번호</th><th>제목</th><th>작성자</th><th>작성시간</th><th>수정시각</th></tr>
	</thead>
	<c:forEach items="${list}" var="bbs">
		<tr><td>${bbs.bbs_id}</td><td>${bbs.title}</td><td>${bbs.writer}</td>
			<td>${bbs.created}</td><td>${bbs.updated}</td></tr>
	</c:forEach>
	</table>
</td><tr>
<tr><td>
<c:if test="${logined eq '1'}">
	<input type=button value="새 글쓰기" id=btnNew>	
</c:if>
</td></tr>	
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
.on('click','#btnNew',function(){
	document.location="/app/new";
	return false;
})
 .on('click','#btnlogin',function(){
 	document.location="/app/login";
 	return false;
 })
 .on('click','#doloout',function(){
	 if(confirm("로그아웃 하시겟습니까")) return true;
     else return false;
 })
 .on('click','#doloout',function(){
 	document.location="/app/login";
 	return false;
 })
</script>
</body>
</html>