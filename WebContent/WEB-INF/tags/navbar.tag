<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag dynamic-attributes="current"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-3">
	<a class="navbar-brand" href="${ctxPath }">중고세상</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
		<div class="navbar-nav mr-auto">
			<a class="nav-link nav-item ${current.home }" href="${ctxPath }">Home</a>
			<a class="nav-link nav-item ${current.list }"
				href="${ctxPath }/product/list.do">목록</a>
		</div>

		<div class="navbar-nav ">
			<c:if test="${not empty sessionScope.authUser }">
				<a class="nav-link nav-item ${current.write }"
					href="${ctxPath }/notice/list.do">공지사항</a>
				<a class="nav-link nav-item ${current.write }"
					href="${ctxPath }/contactUs.do">Contact US</a>
				<!--<a class="nav-link nav-item ${current.write }"
					data-toggle="modal" href="#reportModal">Contact US</a> -->
				<a class="nav-link nav-item ${current.write }"
					href="${ctxPath }/myPage.do">MyPage( ${authUser.name} [ id: ${authUser.id} ]님 )</a>
				<a class="nav-link nav-item ${current.write }"
					href="${ctxPath }/product/write.do">작성</a>
				<a class="nav-link nav-item ${current.logout }"
					href="${ctxPath }/logout.do">로그아웃</a>
			</c:if>

			<c:if test="${empty sessionScope.authUser }">
				<a class="nav-link nav-item ${current.join }"
					href="${ctxPath }/join.do">회원가입</a>
				<a class="nav-link nav-item ${current.login }"
					href="${ctxPath }/login.do">로그인</a>
			</c:if>

		</div>
	</div>
</nav>