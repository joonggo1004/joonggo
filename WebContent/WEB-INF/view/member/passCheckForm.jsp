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

<title>패스워드체크</title>
</head>
<body>
<u:navbar myPage="active" />

	<div class="container">

		<form action="myPage.do" method="post">

			<div class="form-group">
				<label for="input1">암호</label>
					<input type="password" class="form-control" name="password" id="input1" required>
				<small class="form-text text-muted">
					<c:if test="${errors.password }">암호를 입력하세요.</c:if>
					<c:if test="${errors.pwNotMatch }">암호가 일치하지 않습니다.</c:if>
				</small>
			</div>

			<input class="btn btn-primary" type="submit" value="확인" />

		</form>
	</div>

<u:footer />

</body>
</html>