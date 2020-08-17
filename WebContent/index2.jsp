<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="container mt-3">
	<div class="jumbotron">
		<u:isLogin>
		<h1 class="display-4">${authUser.name }님,</h1>
			<p class="lead"><b>1년동안 안 쓰는 물건은 평생 안 씁니다. 안 쓰는 물건을 찾아서 거래해 보십시요. 쏠쏠한 재미와 함께 금전적인 수입이 찾아 올 것입니다.</b></p>
			<hr class="my-4">
			<br />
			<c:if test="${authUser.emailChecked }">
				<a class="btn btn-primary btn-lg" href="${ctxPath }/product/list.do" role="button">목록보기</a>
			</c:if>
			<c:if test="${!authUser.emailChecked }">
				<p class="lead">이메일 인증이 안 되어 있습니다. 이메일 인증해 주시기 바랍니다.</p>
				<a class="btn btn-primary btn-lg" href="${ctxPath }/member/emailAuthSend.do" role="button">이메일 인증하러 가기</a>
			</c:if>
		</u:isLogin>
		<u:notLogin>
		<h1 class="display-4">중고세상</h1>
		<p class="lead">1년동안 안 쓰는 물건은 평생 안 씁니다. 안 쓰는 물건을 찾아서 거래해 보십시요. 쏠쏠한 재미와 함께 금전적인 수입이 찾아 올 것입니다.</p>
		<hr class="my-4">
		<br />
			<p>회원 가입부터 시작해보세요.</p>
			<a class="btn btn-primary btn-lg" href="join.do" role="button">회원 가입</a>
			<a class="btn btn-primary btn-lg" href="login.do" role="button">로그인</a>
		</u:notLogin>
	</div>
</div>