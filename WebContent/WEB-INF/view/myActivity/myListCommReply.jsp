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

<title>게시글 목록</title>
</head>
<body>
<u:navbar myPage="active" />
	<div class="container"><div class="col-sm-12" style="color: white; text-align: center;"><h4>내가 쓴 댓글들</h4></div></div>
	<div class="container">
		<table class="table table-dark table-striped">
			<thead>
				<tr>
					<th style="width: 10%;">번호</th>
					<th style="width: 10%;">게시글번호</th>
					<th style="width: 10%;">댓글번호</th>
					<th style="width: 10%;">작성자</th>
					<th style="width: 40%;">내용</th>
					<th style="width: 20%;">작성일자</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${viewData.isEmpty() }">
					<tr>
						<td colspan="6">댓글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="message" items="${viewData.messageList }">
					<tr>
						<td>${message.no }</td>
						<td><a	href="${ctxPath }/product/read.do?no=${message.productNo }&pageNo=1">
							<c:out value="${message.productNo }"></c:out></a></td>
						<td>
							<c:if test="${message.parentNo == 0}">
								게시글 댓글
							</c:if>
							<c:if test="${message.parentNo != 0}">
								${message.parentNo }
							</c:if>
						</td>
						<td>${message.guestName }</td>
						<td>${message.message }</td>
						<td>${message.regDate.toLocaleString() }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container my-3 p-3">
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
			<c:forEach begin="1" end="${viewData.pageTotalCount }" var="i">  
		    	<c:if test="${viewData.currentPageNumber == i}">
			    	<li class="page-item active">
			    		<a class="page-link" href="${ctxPath }/myPage/myCommReply.do?page=${i }">${i }</a>
			    	</li>
		    	</c:if>
		    	<c:if test="${viewData.currentPageNumber != i}">
		    		<li class="page-item">
		    			<a class="page-link" href="${ctxPath }/myPage/myCommReply.do?page=${i }">${i }</a>
		    		</li>
		    	</c:if>
		    </c:forEach>
		  </ul>
		</nav>
	</div>
	
<u:footer />

</body>
</html>