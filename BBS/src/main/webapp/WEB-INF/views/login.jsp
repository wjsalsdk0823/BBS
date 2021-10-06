<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
</head>
<body>
<form id=frmlign method="post" action="/app/logincheck">
<table>
<tr>
	<td align=right>아이디</td><td><input type=text name=loginid></td>
</tr>
<tr>
	<td align=right>비밀번호</td><td><input type=text name=passcode></td>
</tr>
<tr>
	<td align=center colspan=2>
	<input type="submit" value="로그인">
	<input type=button value="최소" id=clear type=reset>
</td>
</tr>
<tr>

	<td align=center colspan=2>
	<input type=button value="목록보기" id=showlist>
	<input type=button value="회원가입" id=newbie>
</td>
</tr>
</table>
</form>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
/* .on('click','',function(){
	let bbs_id=$(this).find('td:eq(0)').text();//this는 윗줄tr을 의미한다//find제일 왼쪽의 
	console.log('bbs_id ['+bbs_id+']');
	document.location="/app/view/"+bbs_id;//다큐멘티로케이션 서버로 날아간다?
	return false;
}) */
//아래 회원가입 누르면 sign화면으로 이동
.on('click','#newbie',function(){
 	document.location="/app/sign";
 	return false;
 })
 //아래 목록버튼 누르면 list화면으로 이동
 .on('click','#showlist',function(){
 	document.location="/app/list";
 	return false;
 })
 
</script>
</html>