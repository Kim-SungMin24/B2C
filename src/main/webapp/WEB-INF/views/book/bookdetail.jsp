<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<div class="hero page-inner overlay"
	style="background-image: url('/images/bg_main.png')">
	<div class="container">
		<div class="row justify-content-center align-items-center">
			<div class="col-lg-9 text-center mt-5">
				<h1 class="heading" data-aos="fade-up">${book.title }</h1>

				<nav aria-label="breadcrumb" data-aos="fade-up" data-aos-delay="200">
					<ol class="breadcrumb text-center justify-content-center">
						<li class="breadcrumb-item"><a href="/">Home</a></li>
						<li class="breadcrumb-item"><a href="/book/list">대여 리스트</a></li>
						<li class="breadcrumb-item active text-white-50"
							aria-current="page">${book.title }</li>
					</ol>
				</nav>
			</div>
		</div>
	</div>
</div>

<div class="section">
	<div class="container">
		<div class="row justify-content-between">
			<div class="col-lg-7">
				<div class="img-property-slide-wrap">
					<div class="img-property-slide">
						<img src="/resource/img/${book.img }" alt="Image"
							class="img-fluid" />
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<h2 class="heading text-primary">${book.title }</h2>
				<p class="meta">${book.category }</p>
				<p class="text-black-50">
					<b>${book.content }</b>
				</p>
				<sec:authorize access="isAuthenticated()">
					<c:if
						test="${empty book.rentalid and principal.username!=book.memberid.username }">
						<div class="form-group">
							<button type="button" class="btn btn-primary py-2 px-3"
								id="rentBook">대여신청</button>
						</div>
					</c:if>
					<c:if
						test="${!empty book.rentalid or principal.username==book.memberid.username }">
						<div class="form-group">
							<button type="button" class="btn btn-danger py-2 px-3">대여불가</button>
						</div>
					</c:if>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<div class="form-group">
						<button type="button" class="btn btn-primary py-2 px-3"
							onclick="location.href='/login'">로그인</button>
					</div>
				</sec:authorize>

			</div>
		</div>
	</div>
</div>
<script>
<!-- 대여 요청 버튼? 수정 김명준-->
$('#rentBook').on('click', function(){
	$.ajax({
		type : 'get',
		url : '/rent/request/${book.bookid}'
	})
	.done(function(resp){
		if(resp=='success'){
			alert('대여 요청되었습니다')
			location.href="/book/list";
		}else{
			alert('대여 요청 실패 (자기자신, 다른사람이 먼저 요청함)')
			location.href="/book/list";
		}
	})
	.fail(function(){
		alert('실패')
	})
})
<!--끝-->
</script>
<%@ include file="../include/footer.jsp"%>