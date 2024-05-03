<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardVO.boardTitle }</title>
</head>
<body>
	<h2>글 수정 페이지</h2>
	<form action="update" method="POST">
		<div>
			<p>번호 :</p>
			<input type="text" name="boardId" value="${boardVO.boardId }"
				readonly>
		</div>
		<div>
			<p>제목 :</p>
			<input type="text" name="boardTitle" placeholder="제목 입력"
				maxlength="20" value="${boardVO.boardTitle }" required>
		</div>
		<div>
			<p>작성자 : ${boardVO.memberId}</p>

		</div>
		<div>
			<p>내용 :</p>
			<textarea rows="20" cols="120" name="boardContent"
				placeholder="내용 입력" maxlength="300" required>${boardVO.boardContent }</textarea>
		</div>
		<div>
			<input type="submit" value="등록">
		</div>
	</form>
</body>
</html>