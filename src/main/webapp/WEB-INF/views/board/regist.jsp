<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
<head>
 <sec:csrfMetaTags/>
<meta charset="UTF-8">
<title>board2 테스트</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<form action="regist" method="POST" id="boardForm"
		enctype="multipart/form-data">
	
		<!-- input 태그의 name은 dto의 멤버 변수 이름과 동일하게 작성 -->
		<div>
			<p>제목 :</p>
			<input type="text" name="boardTitle" placeholder="제목 입력"
				maxlength="20" required>
		</div>
			<input type="hidden" name = "categoryId" value = "${param.categoryId}"></input>
			 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
		<div>
			<p>작성자 :</p>
			<input type="text" name="memberId" maxlength="10"
				value='${pageContext.request.userPrincipal.name}' readonly required>
		</div>
		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" name="boardContent"
				placeholder="내용 입력" maxlength="300" required></textarea>
		</div>

		<div>
			<p>파일 업로드 :</p>
			<input id="test" type="file" name="file" multiple>
		</div>

		<div>
			<button type="submit" id="btn_insert">등록하기</button>
		</div>

	</form>



	<script>
		$(document).ready(function() {
 			const token = $("meta[name='_csrf']").attr("content");
        	const header = $("meta[name='_csrf_header']").attr("content");
        	const name = $("#userName").val();
        	
        	$.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });

			// 차단할 확장자 정규식 (exe, sh, php, jsp, aspx, zip, alz)
			var blockedExtensions = /\.(exe|sh|php|jsp|aspx|zip|alz)$/i;

			$('#btn_insert').click(function(event) {
				event.preventDefault();
				$('#boardForm').submit();
			});

			$("#test").change(function(event) {
				var fileInput = $(this);
				var files = fileInput.prop('files'); // file 객체 참조

				console.log("fileInput : " + fileInput);
				console.log("files : " + files);
				
				if (files.length > 3) {
		            alert("최대 " + 3 + "개의 파일만 첨부할 수 있습니다.");
		            fileInput.val(''); // 파일 선택 해제
		            return;
		        }

				var formData = new FormData();
				var maxSize = 10 * 1024 * 1024; // 10 MB 

				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					var fileName = file.name;

					console.log("fileName : " + fileName);

					if (blockedExtensions.test(fileName)) { // 차단된 확장자인 경우
						alert("이 확장자의 파일은 첨부할 수 없습니다.");
						fileInput.val(''); // 파일 선택 해제
						return;
					}

					if (file.size > maxSize) {
						alert("파일 크기가 너무 큽니다. 최대 크기는 10MB입니다.");
						fileInput.val(''); // 파일 선택 해제
						return;
					}

					formData.append('file', file);
				}
				console.log("FormData created:", formData);
				$.ajax({
					url : '../board/attach',
					type : 'post',
					data : formData,
					contentType : false,
					processData : false,
					success : function(response) {
						console.log("결과 : " + response);
					    if (!Array.isArray(response)) {
					        console.error("응답이 배열 형태가 아닙니다:", response);
					        return;
					    }
						response.forEach(function(item, index) {
							$('#boardForm').append('<input type="hidden" name="attachVO[' + index + '].attachPath" value="' + item.attachPath + '">');
							$('#boardForm').append('<input type="hidden" name="attachVO[' + index + '].attachRealName" value="' + item.attachRealName + '">');
							$('#boardForm').append('<input type="hidden" name="attachVO[' + index + '].attachChgName" value="' + item.attachChgName + '">');
							$('#boardForm').append('<input type="hidden" name="attachVO[' + index + '].attachExtension" value="' + item.attachExtension + '">');
						});
					},
					error : function(xhr, status, error) {
						console.error("에러 발생: " + error);
					}
				});
			});
		});
	</script>
</body>
</html>
