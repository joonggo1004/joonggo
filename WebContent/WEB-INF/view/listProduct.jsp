<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<title>게시글 목록</title>
</head>
<body>
<div class="container pt-5">
<table border="1" width="100%" style="text-align:center;">
	<tr>
		<td colspan="4"><a href="write.do">[게시글쓰기]</a></td>
	</tr>
	<tr>
		<td>번호</td>
		<td>제목</td>
		<td>작성자</td>
		<td>조회수</td>
	</tr>
<c:if test="${productPage.hasNoProducts() }">
	<tr>
		<td colspan="4">게시글이 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="product" items="${productPage.content }">
	<tr>
		<td>${product.number }</td>
		<td>
		<a href="read.do?no=${product.number }&pageNo=${productPage.currentPage}">
			<c:out value="${product.title }"/>
		</a>
		</td>
		<td>${product.writer.name }</td>
		<td>${product.readCount }</td>
	</tr>
</c:forEach>
<c:if test="${productPage.hasProducts() }">
	<tr>
		<td colspan="4">
			<c:if test="${productPage.startPage > 5 }">
			<a href="list.do?pageNo=${productPage.startPage - 5 }">[이전]</a>
			</c:if>
			<c:forEach var="pNo" begin="${productPage.startPage }" end="${productPage.endPage }">
			<a href="list.do?pageNo=${pNo }">[${pNo }]</a>
			</c:forEach>
			<c:if test="${productPage.endPage < productPage.totalPages }">
			<a href="list.do?pageNo=${productPage.startPage + 5 }">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>
<a href="${ctxPath }/logout.do">[로그아웃하기]</a>
</div>
</body>
</html>