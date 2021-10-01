<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 보기</title>
</head>
<style>
table#tblView {
border-collapse:collapse;
}
table#tblView td {
	border:1px,solid, blue;
}
table#tblView th{
	color:white;
	background-color:black;
	border:1px, solid, yellow;
}
</style>
<body>
<input type="hidden" id=bbs_id value='${post.bbs_id}'>
<table align=center valign=top>
<tr><td colspan=2>
	<table >
	<tr><td>제목</td><td>${post.title}</td></tr>
	<tr><td>내용</td><td>${post.content}</td></tr>
	<tr><td>작성자</td><td>${post.writer}</td></tr>
	<tr><td>작성시각</td><td>${post.created}</td></tr>
	<tr><td>수정시각</td><td>${post.updated}</td></tr>
	</table>
</td></tr>
<tr><td><input type=button id=btnupdate value=수정></td>
<td align=right><input type=button id=btndelete value=삭제></td></tr>
</table>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
//delete방법 첫번째
$(document)
.on('click','#btndelete',function(){
	let bbs_id=$('#bbs_id').val()
	document.location="/app/delete/"+bbs_id;
	return false;
})
/* delete방법 두번째 방법 
$(document)
.on('click','#btnbelete',function(){
doucument.location="/app/delete/"+$('#bbs_id').val();
return false;
}) */
$(document)
.on('click','#btnupdate',function(){
document.location="/app/update/"+$('#bbs_id').val();
alert
return false;
})
</script>
</html>