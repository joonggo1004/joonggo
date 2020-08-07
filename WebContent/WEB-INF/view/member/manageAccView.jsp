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

<title>My Page</title>
</head>
<body>
<u:navbar myPage="active" />

<div class="container mt-3">
	<div class="jumbotron">
		<h1 class="display-4">My Page입니다.</h1>
		<hr class="my-4">
		<br />
		<br />
		<br />
		<a class="btn btn-primary btn-lg" href="${ctxPath }/modifyAccount.do" role="button">계정관리</a>
		<a class="btn btn-primary btn-lg" href="${ctxPath }/myListProduct.do" role="button">나의활동</a>
	</div>
</div>

</body>
</html>