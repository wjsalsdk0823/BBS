<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
<form id=frmlign method="post" action="/app/signin">
<table>
	<tr><td align="right">이름</td><td><input type=text id='realname'name=realname></td>
	<tr><td align="right">아이디</td><td><input type=text id='loginid'name=loginid></td>
	<tr><td align="right">비밀번호</td><td><input type=password id='password1'name=password1></td>
	<tr><td align="right">비밀번호확인</td><td><input type=password id=password2></td>
	<tr><td align="center" colspan=2>
	<input type="submit" value="가입완료" >
	<input type="reset" value="취소" id=clear>
	<input type="button" id=showlist value="목록보기">
</td>
</tr>
</table>
</form>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('click','#showlist',function(){
 	document.location="/app/list";
 	return false;
 })
 .on('click','#clear',function(){
 	document.location="/app/login";
 	return false;
 })
</script>
</html>