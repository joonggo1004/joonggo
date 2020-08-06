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

<title>가입</title>
</head>
<body>
<u:navbar myPage="active" />
<div class="container pt-5">
	<form action="modifyAccount.do" method="post">
		<fieldset>
			<legend>개인 정보 수정</legend>
			<p>
				아이디: ${authUser.id }
			</p>
			<p>
				이름: ${authUser.name }
			</p>
			<p>
				암호: <br /><input type="password" name="password"/>
				<c:if test="${errors.password }">암호를 입력하세요.</c:if>
			</p>
			<p>
				확인: <br /><input type="password" name="confirmPassword"/>
				<c:if test="${errors.confirmPassword }">확인을 입력하세요.</c:if>
				<c:if test="${errors.notMatch }">암호와 확인이 일치하지 않습니다.</c:if>
			</p>

			<p>
				전화번호: <br /><input type="tel" name="phone"/>
				<c:if test="${errors.phone }">전화번호를 입력하세요.</c:if>
			</p>
			<p>
				email: <br /><input type="email" name="email"/>
				<c:if test="${errors.email }">email를 입력하세요.</c:if>
			</p>
			<input type="submit" value="수정"/>
		</fieldset>
	</form>
</div>
</body>
</html>