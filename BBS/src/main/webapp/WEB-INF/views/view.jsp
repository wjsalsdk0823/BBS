<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
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
<tr><td>
<c:if test="${userid eq post.writer}">
<input type=button id=btnupdate value=수정>
</td><td align=right>
<input type=button id=btndelete value=삭제>
</c:if>
</td></tr>
<c:if test="${userid ne ''}">
<tr id=cur> 
	<td>
		<table style="width:100%;">
		<tr>
			<td><textarea rows="5" cols="60" id=reply_content></textarea></td>
			<td><input type=button id=btnAddReply value='댓글등록'></td>
		</tr>
		</table>
	</td>
</tr>
</c:if>
<!-- 아래  댓글 등록하고 내용 나오고 새로고침하면 등록한 날짜 나오게-->
<c:forEach items="${reply_list}" var="reply">
<tr><td>
<table style="width:100%;">
<tr><td>${reply.content}</td></tr>
<tr><td>${reply.writer}&nbsp;[${reply.created}]</td></tr>
<tr><td align=right>
<c:if test="${userid eq reply.writer}">
	<input type=button id=btnupdatereply value="수정" reply_id='${reply.reply_id}'>
	<input type=button id=btndeletereply value="삭제" reply_id='${reply.reply_id}'>
	</c:if>
<tr><td><hr></td></tr>
</table>
</td></tr>
</c:forEach>
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
return false;
})
//댓글 등록하고 내용 나오는법  DB랑 연결
.on('click','#btnAddReply',function(){
	let pstr=$('#reply_content').val();
	pstr=$.trim(pstr);
	if(pstr=='') return false;
	console.log($('#bbs_id').val()+','+pstr);
	$.post('http://localhost:8080/app/ReplyControl',
			{optype:'add',content:pstr,bbs_id:$('#bbs_id').val()},function(result){
	console.log(result);
	if(result=="fail")return false;
	
	let str='<tr><td>'+pstr+'</td></tr>';
	$('#cur').after(str);
	$('#reply_content').val('');
	},'text')
	return false;
})
.on('click','#btndeletereply',function(){
	if(!confirm("정말로 지울까요")) return false;
	let reply_id=$(this).attr('reply_id');//여기 this는 위에 버튼
	let thisButton=$(this);
	$.post('http://localhost:8080/app/ReplyControl',
			{optype:'delete',reply_id:reply_id},function(result){
		if(result=="ok"){
			//여기 버튼은 Post부터 아래를 의미? 상위 table에서 찾아온다?
			thisButton.closest('table').closest('tr').remove();
		}	
	},'test');
})
</script>
</html>