<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 글쓰기</title>
</head>
<body>
<table align=center valign=top>
<tr><td>
	<form method=POST action="/app/save" id=frmNew> 
	<table>
	<tr><td>제목</td><td><input type=text name=title size=58></td></tr>
	<tr><td valign=top>내용</td>
		<td><textarea name=content rows=20 cols=60></textarea></td></tr>
	<tr><td>작성자</td><td><input type=text name=writer value='${userid}' readonly></td></tr>
	<!-- <tr><td>비밀번호</td><td><input type=password name=passcode></td></tr> -->
	<tr><td colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=submit value='글 등록'>&nbsp;
	<!-- 취소누르면 목록보기로 넘아가도록 한게 아래 제이쿼리 방법두번째 방법 -->
		<input type=button value='취소(목록)' id=clear >
</table>
</form>
</td></tr>
</table>
</body>		
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>

<script >
	$(document)
	.on('click','#clear',function(){
	document.location="/app/list";
	return false;
})
.on('submit','frmNew',function(){
	let title=$('#title').val();
	title=$.trim(title);
	if(title=='') {
		alert("제목을 입력하세요");return false;
	}
	let content=$('#content').val();
	content=$.trim(content);
	if(content=='') {
		alert('내용을 입력하세요');return false;
	}
	return true;
})
</script>	
</table>
</form>
</body>
</html>