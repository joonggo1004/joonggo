<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="container mt-3">
	<div class="jumbotron">
		<h1 class="display-4">중고 세상</h1>
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

<a href="${ctxPath }/notice/read.do?no=2">
	<MARQUEE scrollamount="10" bgColor="lightblue">
		<img src= "images/gonggu.jpg" width="200" height="150">
		<img src= "images/gonggu2.jpg" width="400" height="150">
	</MARQUEE>
</a>
<footer style="background-color: #000000; color: #ffffff">
	<div class="container-fluid">
		<br />
		<div class="row">
			<div class="col-sm-2" style="text-align: center;"><h5>Copyright &copy; 2020</h5><h5>김종찬</h5></div>
			<div class="col-sm-4"><h4>대표자 소개</h4><p>저는 중고세상 대표 김종찬입니다. 삼성전자, 데이콤, SK텔레콤에서 이십수년간 재직 후 새 출발을 하려 합니다. 많은 관심과 애정 부탁 드립니다.</p></div>
			<div class="col-sm-2"><h4 style="text-align: center;">Introduction</h4>
				<div class="list-group">
					<a href="#" class="list-group-item">거래방법</a>
					<a href="#" class="list-group-item">유의사항</a>
				</div>
			</div>
			<div class="col-sm-2"><h4 style="text-align: center;">SNS</h4>
				<div class="list-group">
					<a href="#" class="list-group-item">페이스북</a>
					<a href="#" class="list-group-item">카카오톡</a>
				</div>
			</div>
			<div class="col-sm-2"><h4 style="text-align: center;"><span class="glyphicon glyphicon-ok"></span>&nbsp;by 김종찬</h4></div>
		</div>
	</div>
</footer>