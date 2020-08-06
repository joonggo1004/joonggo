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

<script>
	var expression = "";
	function replyFct(index) {
		expression = "reply"+index;
		document.getElementById(expression).style.display = "block";
	}
</script>

<title>게시글 읽기</title>
</head>
<body>
<u:navbar home="active" />

<div class="container">
	<table class="table" style="text-align:center;">
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
					<!-- <a href="/pjfiles/${productData.product.number }/${productData.fileName}">${productData.fileName}</a>  -->
					<img src="/pjfiles/${productData.product.number }/${productData.fileName}" alt="" />
				</c:if>
			</td>
			<%--<td><u:pre value="${productData.content }"/></td>--%>
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
	</table>
	
	<c:if test="${authUser != null }">
		<form action="${ctxPath }/comment/writeComment.do" method="post">
			<input type="number" name="productNo" value="${productData.product.number }" hidden="hidden" />
			이름: <input type="text" name="id" value="${authUser.id }" hidden="hidden"/>
				<input type="text" name="name" value="${authUser.name }"/> <br />
			메시지: <textarea name="message" cols="30" rows="3"></textarea> <br />
			<input type="submit" value="메시지 남기기" />
		</form>
		
		<c:if test="${!viewData.isEmpty() }">
	
			<table class="table">
				<c:forEach var="message" items="${viewData.messageList }" varStatus="status">
					<tr>
						<td>${message.guestName }(${message.regDate.toLocaleString() }): ${message.message } <br />
							${i=status.index;'' }
							<button type="button" onclick="replyFct(${i})">Reply</button>
							<form id="reply${i }" style="display:none" action="${ctxPath }/comment/writeReply.do" method="post">
								<input type="number" name="productNo" value="${productData.product.number }" hidden="hidden" />
								<input type="number" name="parentNo" value="${message.no }" hidden="hidden" />
								이름: <input type="text" name="id" value="${authUser.id }" hidden="hidden"/>
									<input type="text" name="name" value="${authUser.name }"/> <br />
								메시지: <textarea name="message" cols="30" rows="3"></textarea> <br />
								<input type="submit" value="reply남기기" />
							</form>
							<c:if test="${message.no == viewReplyData.messageList[0].parentNo }">
							<table class="table">
								<c:forEach var="reply" items="${viewReplyData.messageList }" >
									<tr>
										<td>
											${reply.guestName }(${reply.regDate.toLocaleString() }): ${reply.message } <br />
										</td>
									</tr>
								</c:forEach>
							</table>
							</c:if>
							
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:forEach var="pageNum" begin="1" end="${viewData.pageTotalCount }">
			<a href="${ctxPath }/product/read.do?no=${productData.product.number}&&page=${pageNum }">[${pageNum }]</a>
			</c:forEach>
		
		</c:if>
	</c:if>
</div>
</body>
</html>