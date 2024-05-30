<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board2 테스트</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
		<div>
			<p>작성자 :</p>
			<input type="text" name="memberId" maxlength="10"
				value='${sessionScope.memberId }' readonly required>
		</div>
		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" name="boardContent"
				placeholder="내용 입력" maxlength="300" required></textarea>
		</div>

		<div>
			<p>파일 업로드 :</p>
			<input id="test" type="file" name="file">
		</div>

		<div>
			<button type="submit" id="btn_insert">등록하기</button>
		</div>

	</form>



	<script>
		$(document).ready(function() {
			// 차단할 확장자 정규식 (exe, sh, php, jsp, aspx, zip, alz)
			var blockedExtensions = /\.(exe|sh|php|jsp|aspx|zip|alz)$/i;

			$('#btn_insert').click(function(event) {
				event.preventDefault();
				$('#boardForm').submit();
			});

			$("#test").change(function(event) {
				var fileInput = $(this);
				var file = fileInput.prop('files')[0]; // file 객체 참조
				var fileName = fileInput.val();

				console.log("fileInput : " + fileInput);
				console.log("file : " + file);
				console.log("fileName : " + fileName);

				if (file) { // 파일이 선택된 경우에만 체크
					if (blockedExtensions.test(fileName)) { // 차단된 확장자인 경우
						alert("이 확장자의 파일은 첨부할 수 없습니다.");
						fileInput.val(''); // 파일 선택 해제
						return;
					}

					var maxSize = 10 * 1024 * 1024; // 10 MB 
					if (file.size > maxSize) {
						alert("파일 크기가 너무 큽니다. 최대 크기는 10MB입니다.");
						fileInput.val(''); // 파일 선택 해제
						return;
					}
				}

				var formData = new FormData();
				formData.append('file', file);

				$.ajax({
					url : '../board/attach',
					type : 'post',
					data : formData,
					contentType : false,
					processData : false,
					success : function(response) {
						console.log("결과 : " + response);
						
						$('#boardForm').append('<input type="hidden" name="attachVO.attachPath" value=" '+response.attachPath+' ">');
                        $('#boardForm').append('<input type="hidden" name="attachVO.attachRealName" value="' + response.attachRealName + '">');
                        $('#boardForm').append('<input type="hidden" name="attachVO.attachChgName" value="' + response.attachChgName + '">');
                        $('#boardForm').append('<input type="hidden" name="attachVO.attachExtension" value="' + response.attachExtension + '">');
						
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
