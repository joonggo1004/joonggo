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
	function writeCommentFunction() {
		$.ajax({
			type: 'POST',
			dataType:"json",
			url: './WriteAjaxComment.do',
			data: {productNo:$("#productNo").val(),
				id:$("#id").val(),
				name:$("#name").val(),
				message:$("#message").val()},
			success: function(result) {
				if (result == 1) {
					;
				} else {
					;
				}
				
			
			}
		});
	}
</script>

<title>게시글 읽기</title>
</head>
<body>
<u:navbar list="active" />

<div class="container">
	<table class="table table-dark table-striped">
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
<img src="/pjfiles/product/${productData.product.number }/${productData.fileName}" alt="" />
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
	</table>
	
	<c:if test="${authUser != null }">
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
		
		<c:if test="${!arrayProdReplyData[0].isEmpty() }">
	
			<table class="table">
				<c:forEach var="message" items="${arrayProdReplyData[0].messageList }" varStatus="status">
					<tr>
						<td>
							${i=status.index;'' }
							<button class="btn btn-outline-primary" type="button" onclick="replyFct(${i})">응답</button> 
							
							<form class="form-inline" id="reply${i }" style="display:none" action="${ctxPath }/prodComment/writeReply.do" method="post">
								<input type="number" name="index" value="${i }" hidden="hidden" />
								<input type="number" name="productNo" value="${productData.product.number }" hidden="hidden" />
								<input type="number" name="parentNo" value="${message.no }" hidden="hidden" />
								<div class="form-group">
									${authUser.name }: <input type="text" name="id" value="${authUser.id }" hidden="hidden"/>
										<input type="text" name="name" value="${authUser.name }" hidden="hidden"/> 
									<textarea class="form-control" name="message" cols="30" rows="2"></textarea>
									<c:if test="${(index == i) && errors.message }">
										<b style="color:red">내용을 입력하세요.</b>
										<script>replyFct(${i });</script>
									</c:if>
									<input class="btn btn-outline-secondary" type="submit" value="응답 남기기" />
								</div>
							</form>
							
							<c:forEach var="view" items="${arrayProdReplyData }" varStatus="status">
								${j=status.index;'' }
								<c:if test="${(j!=0) && !view.isEmpty() }">
									<c:if test="${message.no == view.messageList[0].parentNo }">
									<table class="table">
										<c:forEach var="reply" items="${view.messageList }" >
											<tr>
												<td>
													응답: ${reply.guestName }(${reply.regDate.toLocaleString() }): ${reply.message } <br />
												</td>
											</tr>
										</c:forEach>
									</table>
									</c:if>
								</c:if>
							</c:forEach>
							
							댓글: ${message.guestName }(${message.regDate.toLocaleString() }): ${message.message }
						</td>
					</tr>
				</c:forEach>
			</table>
			
			<c:forEach var="pageNum" begin="1" end="${arrayProdReplyData[0].pageTotalCount }">
			<a href="${ctxPath }/product/read.do?no=${productData.product.number}&&page=${pageNum }">[${pageNum }]</a>
			</c:forEach>
		
		</c:if>
	</c:if>
</div>
</body>
</html>