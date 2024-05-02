<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<body>
	<h2>회원 가입 페이지</h2>
	
	<form action="regist" Method="post">
		<input name="memberName" type="text" placeholder="아이디를 입력해주세요" required="required">
		<input name="memberPassword" type="password" placeholder="비밀번호를 입력하세요." required="required">
		<input name="memberEmail" type="email" required="required" placeholder="이메일을 입력해">
		<input type="submit" value="등록하기">
	</form>
	
</body>
</html>