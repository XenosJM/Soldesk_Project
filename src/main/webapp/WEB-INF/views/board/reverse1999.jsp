<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시판 메인 페이지</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #f7f7f7;
}

.container {
	max-width: 800px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.logo {
	text-align: center;
	margin-bottom: 20px;
}

.logo a {
	text-decoration: none;
	font-size: 24px;
	color: #333;
}

h1 {
	text-align: center;
	margin-bottom: 20px;
}

.search-container {
	display: flex;
	align-items: center;
	margin-bottom: 20px;
}

.search-container select {
	margin-right: 10px;
	padding: 5px;
	border-radius: 5px;
}

.search-container input[type="text"] {
	flex-grow: 1;
	padding: 8px;
	border-radius: 5px;
	border: 1px solid #ccc;
}

.search-container input[type="submit"] {
	padding: 8px 15px;
	background-color: #4caf50;
	color: #fff;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

.search-container input[type="submit"]:hover {
	background-color: #45a049;
}

.register-button {
	display: block;
	width: 100px;
	padding: 10px;
	background-color: #007bff;
	color: #fff;
	text-align: center;
	text-decoration: none;
	border-radius: 5px;
	margin: 0 auto 20px;
}

.register-button:hover {
	background-color: #0056b3;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: center;
}

th {
	background-color: #f2f2f2;
}

ul {
	list-style-type: none;
	text-align: center;
}

ul li {
	display: inline-block;
	margin-right: 10px;
}

ul li a {
	text-decoration: none;
	color: #333;
}
</style>
</head>
<body>
	<div class="container">
		<div class="logo">
			<a href="/ex01/">메인페이지</a>
		</div>
		<h1>게시판</h1>
		
		<!-- 등록 버튼 추가 -->
		<form method="get" action=regist>
			<input type="hidden" name = "categoryId" value='1'>
			<input type="submit" value = "등록">
		</form>

		<form class="search-container" method="get" action="search">
			<select name="searchOption" id="searchOption">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="search" id="searchKey" placeholder="검색어를 입력하세요">
			<input type="submit" value="검색">
		</form>

		<table>
			<thead>
				<tr>
					<th style="width: 60px">번호</th>
					<th style="width: 700px">제목</th>
					<th style="width: 120px">작성자</th>
					<th style="width : 120px">댓글수</th>
					<th style="width: 100px">작성일</th>
				</tr>
			</thead>
			<tbody id="boardList">
				<c:forEach var="BoardVO" items="${boardList}">
					<tr>
						<td>${BoardVO.boardId}</td>
						<td><a href="detail?boardId=${BoardVO.boardId}">${BoardVO.boardTitle}</a></td>
						<td>${BoardVO.memberId}</td>
						<td>${BoardVO.boardReplyCount }</td>
						<fmt:formatDate value="${BoardVO.boardRegistDate}"
							pattern="yyyy-MM-dd HH:mm:ss" var="boardRegistDate" />
						<td>${boardRegistDate}</td	>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<ul>
			<c:if test="${pageMaker.isPrev()}">
				<li><a href="list?pageNum=${pageMaker.startNum - 1}">이전</a></li>
			</c:if>
			<c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}"
				var="num">
				<li><a href="list?pageNum=${num}">${num}</a></li>
			</c:forEach>
			<c:if test="${pageMaker.isNext()}">
				<li><a href="list?pageNum=${pageMaker.endNum + 1}">다음</a></li>
			</c:if>
		</ul>
	</div>

</body>
</html>