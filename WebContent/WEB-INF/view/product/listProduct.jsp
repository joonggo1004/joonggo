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

<title>게시글 목록</title>
</head>
<body>
<u:navbar list="active" />
	<div class="container">
		<table class="table">
			<thead>
				<tr>
					<th style="width: 10%;">번호</th>
					<th style="width: 50%;">제목</th>
					<th style="width: 10%;">작성자</th>
					<th style="width: 20%;">작성일자</th>
					<th style="width: 10%;">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${productPage.hasNoProducts() }">
					<tr>
						<td colspan="5">게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="product" items="${productPage.content }">
					<tr>
						<td>${product.number }</td>
						<td><a href="${ctxPath }/product/read.do?no=${product.number }">
								<c:out value="${product.title }"></c:out>
						</a></td>
						<td>${product.writer.name }</td>
						<td>${product.regDate.toLocaleString() }</td>
						<td>${product.readCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container mt-3">
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<c:if test="${productPage.startPage > 5 }">
					<li class="page-item disabled"><a class="page-link"
						href="${ctxPath }/product/list.do?pageNo=${productPage.startPage-5 }"
						tabindex="-1" aria-disabled="true">이전</a></li>
				</c:if>
				<c:forEach var="pNo" begin="${productPage.startPage }"
					end="${productPage.endPage }">
					<c:if test="${productPage.currentPage == pNo}">
						<li class="page-item active"><a class="page-link" href="${ctxPath }/product/list.do?pageNo=${pNo }">${pNo }</a></li>
					</c:if>
					<c:if test="${productPage.currentPage != pNo}">
						<li class="page-item"><a class="page-link" href="${ctxPath }/product/list.do?pageNo=${pNo }">${pNo }</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${productPage.endPage < productPage.totalPages }">
					<li class="page-item"><a class="page-link"
						href="${ctxPath }/product/list.do?pageNo=${productPage.startPage + 5 }">다음</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
	<div class="container">
		<form action="list.do" method="get" class="form-inline my-2 my-lg-0 float-right">
			<input type="text" name="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search"/>
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
		</form>
	</div>
</body>
</html>