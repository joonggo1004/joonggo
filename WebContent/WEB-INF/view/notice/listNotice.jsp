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

<title>공지사항 게시글 목록</title>
</head>
<body>
<u:navbar notice="active" />
	<div class="container"><div class="col-sm-12" style="color: white; text-align: center;"><h4>공지사항</h4></div></div>
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
				<c:if test="${noticePage.hasNoNotices() }">
					<tr>
						<td colspan="5">게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="notice" items="${noticePage.content }">
					<tr>
						<td>${notice.number }</td>
						<td><a
							href="${ctxPath }/notice/read.do?no=${notice.number }">
								<c:out value="${notice.title }"></c:out>
						</a></td>
						<td>${notice.writer.name }</td>
						<td>${notice.regDate.toLocaleString() }</td>
						<td>${notice.readCount }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container mt-3">
		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<c:if test="${noticePage.startPage > 5 }">
					<li class="page-item disabled"><a class="page-link"
						href="${ctxPath }/notice/list.do?pageNo=${noticePage.startPage-5 }"
						tabindex="-1" aria-disabled="true">이전</a></li>
				</c:if>
				<c:forEach var="pNo" begin="${noticePage.startPage }"
					end="${noticePage.endPage }">
					<c:if test="${noticePage.currentPage == pNo}">
						<li class="page-item active"><a class="page-link" href="${ctxPath }/notice/list.do?pageNo=${pNo }">${pNo }</a></li>
					</c:if>
					<c:if test="${noticePage.currentPage != pNo}">
						<li class="page-item"><a class="page-link" href="${ctxPath }/notice/list.do?pageNo=${pNo }">${pNo }</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${noticePage.endPage < noticePage.totalPages }">
					<li class="page-item"><a class="page-link"
						href="${ctxPath }/notice/list.do?pageNo=${noticePage.startPage + 5 }">다음</a></li>
				</c:if>
			</ul>
		</nav>
	</div>
	<div class="container inline">
		<form action="list.do" method="get" class="form-inline my-2 my-lg-0 float-right">
			<input type="text" name="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search"/>
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
		</form>
		<a class="btn btn-danger float-right" href="write.do">작성</a>
	</div>

<u:footer />

</body>
</html>