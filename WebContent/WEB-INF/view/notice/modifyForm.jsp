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

<title>게시글 수정</title>
</head>
<body>
<u:navbar notice="active" />

	<div class="container">
		<form action="modify.do?no=${modReq.noticeNumber }" method="post" enctype="multipart/form-data" >
			
			<p>번호: ${modReq.noticeNumber }</p>
				<%-- <input type="number" name="no" readonly="readonly" value="${modReq.noticeNumber }" hidden/>--%>
				
			<div class="form-group">
				<label for="input1">제목</label>
					<input type="text" class="form-control" name="title" id="input1" placeholder="제목을 입력하세요." value="${modReq.title }" required>
				<small class="form-text text-muted">
					<c:if test="${errors.title }">제목을 입력하세요.</c:if>
				</small>
			</div>
			
			<div class="form-group">
				<label for="textarea1">내용</label>
				<textarea class="form-control" name="content" id="textarea1"
					rows="3">${modReq.content }</textarea>
			</div>
			<img src="/pjfiles/${modReq.noticeNumber }/${modReq.fileName}" alt="" />
			
			<p>
				파일: 기존에 등록된 파일이 있다면 지금 등록하는 파일로 대체됩니다.<br />
				<input type="file" name="file1" /> <%--accept="image/*" : image file로만 제한 --%>
			</p>
			<input class="btn btn-secondary" type="submit" value="수정" />
		</form>
	</div>

<u:footer />

</body>
</html>