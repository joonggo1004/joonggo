<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctxPath }/css/custom.css">

<script>
	var expression = "";
	var pageNo = 1;
	var liNo = "1";
	function replyFct(index) {
		expression = "reply"+index;
		document.getElementById(expression).style.display = "block";
	}
	function writeCommentFunction() {
		var productNo = "${productData.product.number }";
		pageNo = 1;
		$.ajax({
			type: 'POST',
			dataType:"json",
			url: './writeAjaxComment.do',
			data: {
				productNo:productNo,
				id:"${authUser.id }",
				name:"${authUser.name }",
				message:$("#message").val(),
			},
			success: function(result) {
				if (result == 1) {
					$('#pagelist').load(location.href+'#pagelist #pagelistChild');
					listCommentFunction(productNo, pageNo);
				}
			}
		});
		$('#message').val('');
	}
	function listCommentFunction(product, page) {
		$('#commentList').text('');
		productNo = product;
		pageNo = page;
		$.ajax({
			type: "POST",
			url: "./listAjaxComment.do",
			data: {
				productNo: productNo,
				pageNo: pageNo
			},
			success: function(data) {
				if(data == "") return;
				var parsed = JSON.parse(data);
				var result = parsed.result;
				$('#commentList').prepend('<table class="table table-dark table-striped">');
				for(var i = result.length-1; i >=0 ; i--) {
					addView(result[i][0].value, result[i][1].value, result[i][2].value);
				}
				$('#commentList').append('</table>');
			}
		});
		if(pageNo == 1 || String(pageNo) != liNo){
			$('#'+liNo).removeClass('active');
			liNo = String(pageNo);
			$('#'+liNo).addClass('active');	
		}
	}
	function addView(userName, commentTime, message) {
		$('#commentList .table').prepend(
				'<tr>'+
				'<td>'+
				'<button class="btn btn-danger" type="button">응답</button>'+
				'<span> 댓글: '+userName+'('+commentTime+'): '+message+'</span>'+
				'</td>'+
				'</tr>'
		);
		$('#commentList').scrollTop($('#commentList')[0].scrollHeight);
	}
	$(function(){
		var productNo = "${productData.product.number }";
		var pageNo = 1;
		listCommentFunction(productNo, pageNo);
	});
</script>

<title>게시글 읽기</title>
</head>
<body>
<u:navbar listAjax="active" />

<div class="container">
	<table class="table table-dark table-striped">
		<thead>
				<tr>
					<th style="width: 10%; text-align: center;">구분</th>
					<th style="width: 90%; text-align: center;">내용</th>
				</tr>
		</thead>
		<tbody>
			<tr>
				<td>번호</td>
				<td>${productData.product.number }</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${productData.product.writer.name }</td>
			</tr>
			<tr>
				<td>제목</td>
				<td><c:out value="${productData.product.title }"/></td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${productData.product.regDate.toLocaleString() }</td>
			</tr>
			<tr>
				<td>수정일</td>
				<td>${productData.product.modifiedDate.toLocaleString() }</td>
			</tr>
			<tr>
				<td>내용</td>
				 
				<td style="white-space: pre-wrap;">
<c:out value="${productData.content }"/>
<c:if test="${not empty productData.fileName}">
<!-- <a href="/pjfiles/product/${productData.product.number }/${productData.fileName}">${productData.fileName}</a>  -->
<img src="/pjfiles/product/${productData.product.number }/${productData.fileName}" alt=""/>
</c:if>
				</td>
				<%--
				<td><u:pre value="${productData.content }"/>
					<c:if test="${not empty productData.fileName}">
						<!-- <a href="/pjfiles/${productData.product.number }/${productData.fileName}">${productData.fileName}</a>  -->
						<img src="/pjfiles/${productData.product.number }/${productData.fileName}" alt="" />
					</c:if>
				</td>
				--%>
			</tr>
			<tr>
				<td colspan="2">
					<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo }"/>
					<c:if test="${authUser.id == productData.product.writer.id }">
						<a class="btn btn-secondary" href="modify.do?no=${productData.product.number }">수정</a>
						<a class="btn btn-danger" href="delete.do?no=${productData.product.number }">삭제</a>
					</c:if>
				</td>
			</tr>
		</tbody>		
	</table>
	
	<c:if test="${authUser.emailChecked }">
		<div class="container">
			
				<input id="productNo" type="number" name="productNo" value="${productData.product.number }" hidden="hidden" />
				<input id="id" type="text" name="id" value="${authUser.id }" hidden="hidden"/>
				<input id="name" type="text" name="name" value="${authUser.name }" hidden="hidden"/>
				
				<div class="form-group">
					<label for="textarea1">${authUser.name }:</label>
					<textarea id="message" class="form-control" name="message" id="textarea1" rows="1"></textarea>
					<small class="form-text text-muted">
						<c:if test="${errors.message }">내용을 입력하세요.</c:if>
					</small>
				</div> 
				<button class="btn btn-primary" type="button" onclick="writeCommentFunction();" >댓글 남기기</button>
			
		</div>
		
		
	
		<div id="commentList" class="portlet-body chat-widget" style="overflow-y:auto; width:auto; height:200px;">
		</div>
		
		<div id="pagelist" class="container mt-3">
			<nav id="pagelistChild" aria-label="Page navigation example">
				<ul class="pagination justify-content-center">
					<c:forEach var="pageNum" begin="1" end="${arrayProdReplyData[0].pageTotalCount }" varStatus="status">
						<li id="${status.index }" class="page-item"><a class="page-link" href="javascript:void(0);" onclick="listCommentFunction(${productData.product.number},${pageNum});">${pageNum }</a></li>
					</c:forEach>
				</ul>
			</nav>
		</div>
	</c:if>
</div>

<u:footer />

</body>
</html>