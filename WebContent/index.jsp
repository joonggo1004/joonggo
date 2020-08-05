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

<title>회원제 게시판 예제</title>
</head>
<body>
<u:navbar home="active" />
<div class="container pt-5">
<u:isLogin>
	${authUser.name }님, 안녕하세요.
	<a href="logout.do">[로그아웃하기]</a>
	<a href="myPage.do">[마이페이지]</a>
	<br />
	<br />
	<a href="${ctxPath }/product/list.do">[게시글목록보기]</a>
	<a href="${ctxPath }/product/write.do">[게시글 작성]</a>
</u:isLogin>
<u:notLogin>
	<a href="join.do">[회원가입하기]</a>
	<a href="login.do">[로그인하기]</a>
</u:notLogin>
<br />
<%-- <div><img src="/images/bird.jpg" alt="" /></div> --%>
</div>
</body>
</html>