<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		HTTP 정보 읽기 및 설정
	</div>
	<div class="card-body">
		<div class="card m-2">
			<div class="card-header">
				HTTP 헤더 읽기 및 설정
			</div>
			<div class="card-body">
				<a href="getheadervalue" class="btn btn-danger btn-sm">요청</a>
			</div>
		</div>
		
		<div class="card m-2">
			<div class="card-header">
				쿠키 생성 및 읽기
			</div>
			<div class="card-body">
				<a href="createcookie" class="btn btn-danger btn-sm">쿠키 생성</a>
				<a href="getcookie" class="btn btn-danger btn-sm">쿠키 읽기(서버)</a>
				<a href="getcookie2" class="btn btn-danger btn-sm">쿠키 읽기(서버)</a>
				<a href="#" class="btn btn-danger btn-sm" onclick="getCookie()">쿠키 읽기(자바스크립트)</a>
				<hr/>
				<a href="createjsoncookie" class="btn btn-danger btn-sm">JSON 쿠키 생성</a>
				<a href="getjsoncookie" class="btn btn-danger btn-sm">JSON 쿠키 읽기</a>
				<hr/>
				<a href="createjwtcookie" class="btn btn-danger btn-sm">JWT 쿠키 생성</a>
				<a href="getjwtcookie" class="btn btn-danger btn-sm">JWT 쿠키 읽기</a>
				
 			</div>
			<script>
				function getCookie() {
					console.log(document.cookie);
				}
			</script>
		</div>		
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>