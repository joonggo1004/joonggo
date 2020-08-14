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
<link rel="stylesheet" href="${ctxPath }/css/jumbotron.css">

<title>가입 완료</title>
</head>
<body>

<u:navbar join="active" />

<div class="container mt-3">
	<div class="jumbotron">
		<h1 class="display-4">${param.name }님,<br />중고세상 회원 가입에 성공했습니다.</h1>
		<p class="lead">1년동안 안 쓰는 물건은 평생 안 씁니다. 안 쓰는 물건을 찾아서 거래해 보십시요. 쏠쏠한 재미와 함께 금전적인 수입이 찾아 올 것입니다.</p>
		<hr class="my-4">
		<br />
		<u:isLogin>
			<a class="btn btn-primary btn-lg" href="${ctxPath }/product/list.do" role="button">목록보기</a>
		</u:isLogin>
		<u:notLogin>
			<p>회원 가입부터 시작해보세요.</p>
			<a class="btn btn-primary btn-lg" href="join.do" role="button">회원 가입</a>
			<a class="btn btn-primary btn-lg" href="login.do" role="button">로그인</a>
		</u:notLogin>
	</div>
</div>

</body>
</html>