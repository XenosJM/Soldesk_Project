<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<body>
	<h2>${memberVO.memberId}님의 정보입니다.</h2>
	<p>현재 이메일 : ${memberVO.memberEmail }</p><br>
					
	<c:choose>
		<c:when test="${empty memberVO.memberProperty }"><label for="buyProperty">현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<!-- //TODO 변경할 예정 -->
			<input type="submit" id="buyProperty" onclick="location.href='http://localhost:8080/ex01/shop/imoji'" value="상점"></label><br></c:when>
		<c:when test="${not empty memberVO.memberProperty }"><p>현재 보유중인 상품 목록 : ${memberVO.memberProperty }</p><br></c:when>
	</c:choose>	
	<p>회원가입 날짜 : ${memberVO.memberRegistDate }</p><br>
	<button id="modify">수정하기</button><br>
	<button id="delete">회원탈퇴</button>
	
	<form id="modifyForm" action="modify" Method="post">	
		<input type="password" placeholder="비밀번호 입력"><br>
		<c:choose>
		<c:when test="${not empty memberVO.memberProperty }"><p>현재 보유중인 상품 목록 : ${memberVO.memberProperty }</p><br></c:when>
		<c:when test="${empty memberVO.memberProperty }"><label for="buyProperty">현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<!-- //TODO 변경할 예정 -->
			<input type="submit" id="buyProperty" onclick="location.href='http://localhost:8080/ex01/shop/imoji'" value="상점"></label><br></c:when>
		</c:choose>
		<input type="email" placeholder="이메일을 입력">
		<input type="submit" value="수정하기">
	</form>
	
 	<script type="text/javascript">
		$(document).ready(function(){
			$('#modifyForm').hide();
			
			$('#modify').click(function(){
				var isConfirmed = confirm('회원정보를 수정하시겠습니까?');
				if(isConfirmed){
					let showModifyForm = $('#modifyForm').show();
					let modifyDetail = prompt(showModifyForm);
					confirm(modifyDetail);
				}
			}); // end modify
			
		});// end document
	</script>
</body>
</html>