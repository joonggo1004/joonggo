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
<u:navbar list="active" />

<div class="container">
	<table class="table table-dark table-striped">
		<tr>
			<td>번호</td>
			<td>${noticeData.notice.number }</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${noticeData.notice.writer.name }</td>
		</tr>
		<tr>
			<td>제목</td>
			<td><c:out value="${noticeData.notice.title }"/></td>
		</tr>
		<tr>
			<td>작성일</td>
			<td>${noticeData.notice.regDate.toLocaleString() }</td>
		</tr>
		<tr>
			<td>수정일</td>
			<td>${noticeData.notice.modifiedDate.toLocaleString() }</td>
		</tr>
		<tr>
			<td>내용</td>
			 
			<td style="white-space: pre-wrap;">
<c:out value="${noticeData.content }"/>
<c:if test="${not empty noticeData.fileName}">
					<!-- <a href="/pjfiles/${noticeData.notice.number }/${noticeData.fileName}">${noticeData.fileName}</a>  -->
<img src="/pjfiles/notice/${noticeData.notice.number }/${noticeData.fileName}" alt="" />
</c:if>
			</td>
			<%--
			<td><u:pre value="${noticeData.content }"/>
				<c:if test="${not empty noticeData.fileName}">
					<!-- <a href="/pjfiles/${noticeData.notice.number }/${noticeData.fileName}">${noticeData.fileName}</a>  -->
					<img src="/pjfiles/${noticeData.notice.number }/${noticeData.fileName}" alt="" />
				</c:if>
			</td>
			--%>
		</tr>
		<tr>
			<td colspan="2">
				<c:set var="pageNo" value="${empty param.pageNo ? '1' : param.pageNo }"/>
				<c:if test="${authUser.id == noticeData.notice.writer.id }">
					<a class="btn btn-secondary" href="modify.do?no=${noticeData.notice.number }">수정</a>
					<a class="btn btn-danger" href="delete.do?no=${noticeData.notice.number }">삭제</a>
				</c:if>
			</td>
		</tr>		
	</table>
	
	<c:if test="${authUser != null }">
	<div class="container">
		<form action="${ctxPath }/notComment/writeComment.do" method="post">
			<input type="number" name="noticeNo" value="${noticeData.notice.number }" hidden="hidden" />
			<input type="text" name="id" value="${authUser.id }" hidden="hidden"/>
			<input type="text" name="name" value="${authUser.name }" hidden="hidden"/>
			
			<div class="form-group">
				<label for="textarea1">${authUser.name }:</label>
				<textarea class="form-control" name="message" id="textarea1" rows="1"></textarea>
				<small class="form-text text-muted">
					<c:if test="${errors.message }">내용을 입력하세요.</c:if>
				</small>
			</div> 
			<input class="btn btn-primary" type="submit" value="댓글 남기기" />
		</form>
	</div>
		
		<c:if test="${!arrayNotReplyData[0].isEmpty() }">
	
			<table class="table">
				<c:forEach var="message" items="${arrayNotReplyData[0].messageList }" varStatus="status">
					<tr>
						<td>
							${i=status.index;'' }
							<button class="btn btn-outline-primary" type="button" onclick="replyFct(${i})">응답</button> 
							
							<form class="form-inline" id="reply${i }" style="display:none" action="${ctxPath }/notComment/writeReply.do?page=${arrayNotReplyData[0].currentPageNumber}" method="post">
								<input type="number" name="index" value="${i }" hidden="hidden" />
								<input type="number" name="noticeNo" value="${noticeData.notice.number }" hidden="hidden" />
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
							
							<c:forEach var="view" items="${arrayNotReplyData }" varStatus="status">
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
			
			<div class="container mt-3">
				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-center">
						<c:forEach var="pageNum" begin="1" end="${arrayNotReplyData[0].pageTotalCount }">
							<c:if test="${arrayNotReplyData[0].currentPageNumber == pageNum}">
								<li class="page-item active"><a class="page-link" href="${ctxPath }/notice/read.do?no=${noticeData.notice.number}&&page=${pageNum }">${pageNum }</a></li>
							</c:if>
							<c:if test="${arrayNotReplyData[0].currentPageNumber != pageNum}">
								<li class="page-item"><a class="page-link" href="${ctxPath }/notice/read.do?no=${noticeData.notice.number}&&page=${pageNum }">${pageNum }</a></li>
							</c:if>
						</c:forEach>
					</ul>
				</nav>
			</div>
		
		</c:if>
	</c:if>
</div>
</body>
</html>