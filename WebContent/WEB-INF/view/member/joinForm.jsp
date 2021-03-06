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
<script>
	function passwordCheckFunction() {
		var userPassword1 = $('#input2').val();
		var userPassword2 = $('#input3').val();
		if (userPassword1 != userPassword2){
			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
		} else {
			$('#passwordCheckMessage').html('');
		}
	}
</script>

<title>가입</title>
</head>
<body>

<u:navbar join="active" />
	
	<c:if test="${not empty authUser}">
		<jsp:forward page="/index.jsp"></jsp:forward>
	</c:if>
	<div class="container">

		<form action="join.do" method="post">
			<div class="form-group">
				<label for="input1">아이디</label> <input type="text"
					class="form-control" name="id" id="input1" placeholder="ID를 입력하세요." value="${param.id }"
					required> <small class="form-text text-muted"> <c:if
						test="${errors.id }">ID를 입력하세요.</c:if> <c:if
						test="${errors.duplicateId }">이미 사용중인 아이디입니다.</c:if>
				</small>
			</div>

			<div class="form-group">
				<label for="input2">암호</label> <input type="password"
					class="form-control" name="password" id="input2" required onkeyup="passwordCheckFunction();">

				<small class="form-text text-muted"> <c:if
						test="${errors.password }">암호를 입력하세요.</c:if>
				</small>
			</div>

			<div class="form-group">
				<label for="input3">확인</label> <input type="password"
					class="form-control" name="confirmPassword" id="input3" required onkeyup="passwordCheckFunction();">

				<small class="form-text text-muted"> <c:if
						test="${errors.confirmPassword }">확인을 입력하세요.</c:if> <c:if
						test="${errors.notMatch }">암호와 확인이 일치하지 않습니다.</c:if>
				</small>
				<p style="color: red;" id="passwordCheckMessage"></p>
			</div>
			
			<div class="form-group">
				<label for="input4">이름</label> <input type="text"
					class="form-control" name="name" id="input4" value="${param.name }"
					placeholder="이름을 입력하세요." required> <small
					class="form-text text-muted"> <c:if test="${errors.name }">이름을 입력하세요.</c:if>
				</small>
			</div>
			
			<div class="form-group">
				<label for="input5">전화번호</label> <input type="tel"
					class="form-control" name="phone" id="input5" value="${param.phone }"
					placeholder="전화번호를 입력하세요." required> <small
					class="form-text text-muted"> <c:if test="${errors.phone }">전화번호를 입력하세요.</c:if>
				</small>
			</div>

			<div class="form-group">
				<label for="input6">이메일</label> <input type="email"
					class="form-control" name="email" id="input6" value="${param.email }"
					placeholder="email를 입력하세요." required> <small
					class="form-text text-muted"> <c:if test="${errors.email }">email를 입력하세요.</c:if>
				</small>
			</div>

			<input class="btn btn-primary" type="submit" value="가입" />
			<small class="form-text text-muted">
			<c:if test="${errors.sql }">데이터베이스 오류가 발생했습니다. 데이터베이스를 확인해 주세요.</c:if>
			</small>
			
		</form>
	</div>
<u:footer />

</body>
</html>