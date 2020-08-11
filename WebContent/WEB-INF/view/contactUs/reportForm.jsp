<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/index.jsp" %>

<script>
$(function() {
	$('#reportModal').modal('show');
});
</script>

<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modal">신고하기</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="${ctxPath }/contactUs.do" method="post">
					<div class="form-group">
						<label>신고제목</label>
						<input type="text" name="reportTitle" class="form-control" maxlength="30" />
					</div>
					<div class="form-group">
						<label>신고내용</label>
						<textarea name="reportContent" class="form-control" maxlength="2048" style="height: 180px"></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-danger">신고하기</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>