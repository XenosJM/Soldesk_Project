<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Main</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="#" onclick="window.location.href='/ex01/member/regist'">회원가입</a>
<button id="joinMember" name="joinMember" ></button>

<script type="text/javascript">
	$(function(){
		
	});

</script>

</body>
</html>
