<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업데이트</title>
</head>
<body>
<form method="POST" action="/app/updatue_view">
<input type="hidden" id=bbs_id name=bbs_id value='${post.bbs_id}'>
<table align=center valign=top>
<tr><td colspan=2>
	<table >
	<tr><td>제목</td><td><input type=text id=title name="title" value='${post.title}'></td></tr>
	<tr><td>내용</td><td><input type=text id=content name=content value='${post.content}'></td></tr>
	<tr><td>작성자</td><td><input type=text id=title name="writer" value='${post.writer}'></td></tr>
	<tr><td>작성시각</td><td><input type=text id=title name="created" readonly="readonly" value='${post.created}'></td></tr>
	<tr><td>수정시각</td><td><input type=text id=title name="updated" readonly="readonly" value='${post.updated}'></td></tr>
	</table>
</td></tr>
<tr><td><input type="submit" id=btnupdate value=수정></td>
<td align=right><input type=button id=cancel value=수정취소></td></tr>
</table>
</form>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
//아래는 제이쿼리
$(document)
.on('click','#cancel',function(){
	document.location="/app/list";
	return false;
})
</script>
</html>