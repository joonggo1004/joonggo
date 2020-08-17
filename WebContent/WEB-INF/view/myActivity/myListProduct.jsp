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
	<div class="container"><div class="col-sm-12" style="color: white; text-align: center;"><h4>내가 올린 중고 제품들</h4></div></div>
	<div class="container">
		<table class="table table-dark table-striped">
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
						<td><a
							href="${ctxPath }/product/read.do?no=${product.number }&pageNo=${productPage.currentPage}">
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
						href="${ctxPath }/myPage/myListProduct.do?pageNo=${productPage.startPage-5 }"
						tabindex="-1" aria-disabled="true">이전</a></li>
				</c:if>
				<c:forEach var="pNo" begin="${productPage.startPage }"
					end="${productPage.endPage }">
					<c:if test="${productPage.currentPage == pNo}">
						<li class="page-item active"><a class="page-link" href="${ctxPath }/myPage/myListProduct.do?pageNo=${pNo }">${pNo }</a></li>
					</c:if>
					<c:if test="${productPage.currentPage != pNo}">
						<li class="page-item"><a class="page-link" href="${ctxPath }/myPage/myListProduct.do?pageNo=${pNo }">${pNo }</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${productPage.endPage < productPage.totalPages }">
					<li class="page-item"><a class="page-link"
						href="${ctxPath }/myPage/myListProduct.do?pageNo=${productPage.startPage + 5 }">다음</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
	<div class="container">
		<a class="btn btn-primary btn-lg" href="${ctxPath }/myPage/myCommReply.do" role="button">내 댓글 목록보기</a>
	</div>

<u:footer />

</body>
</html>