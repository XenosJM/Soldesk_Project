<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table, th, td {
	border-style: solid;
	border-width: 1px;
	text-align: center;
}

ul {
	list-style-type: none;
	text-align: center;
}

li {
	display: inline-block;
}
</style>
<meta charset="UTF-8">
<title>게시판 메인 페이지</title>
</head>
<body>
		<div class="logo">
          <a href="/ex01/"><img src="images/logo.png" alt="랩탑로고"> <!-- 로고 이미지 --></a>
        </div>
	<h1>게시판</h1>
	<!-- 글 작성 페이지 이동 버튼 -->
	<a href="regist"><input type="button" value="글 작성"></a>
	<hr>
	<table>
		<thead>
			<tr>
				<th style="width: 60px">번호</th>
				<th style="width: 700px">제목</th>
				<!--  <th style="width: 500px">첨부파일</th> -->
				<th style="width: 120px">작성자</th>
				<th style="width: 100px">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="BoardVO" items="${boardList }">
				<tr>
					<td>${BoardVO.boardId }</td>
					<td><a href="detail?boardId=${BoardVO.boardId }">
					${BoardVO.boardTitle }</a></td>
					<td>${BoardVO.memberId }</td>
					<!-- boardDateCreated 데이터 포멧 변경 -->
					<fmt:formatDate value="${BoardVO.boardRegistDate }"
						pattern="yyyy-MM-dd HH:mm:ss" var="boardRegistDate" />
					<td>${boardRegistDate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>