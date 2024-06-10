<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardVO.boardTitle }</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form id = boardForm action="update" method="POST">
		<div>
			<p>번호 :</p>
			<input type="text" name="boardId" value="${board2VO.boardId }"
				readonly>
		</div>
		<div>
			<p>제목 :</p>
			<input type="text" name="boardTitle" placeholder="제목 입력"
				maxlength="20" value="${board2VO.boardTitle }" required>
		</div>
		<div>
			<p>작성자 : ${board2VO.memberId}</p>

		</div>
		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" name="boardContent"
				placeholder="내용 입력" maxlength="300" required>${boardVO.boardContent }</textarea>
		</div>

		<button type="button" id="fileChange">첨부파일 변경하기</button>
		<br>
		<div>
			<input type="submit" value="등록">
		</div>
	</form>
	
	<script>
		$(document).ready(function(){
			var blockedExtensions = /\.(exe|sh|php|jsp|aspx|zip|alz)$/i;
			
			$('#fileChange').click(function() {
				if (confirm("첨부파일을 변경하시겠습니까?")) {
					if ($('#attach').length === 0) {
						$('<div id="attach"><p>파일 업로드 :</p><input id="test" type="file" name="file" multiple></div>').appendTo('form');
						
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
					}
				}
			});
			
			
			
		});
	</script>
</body>
</html>

