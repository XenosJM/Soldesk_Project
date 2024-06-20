<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--시큐리티 태그를 사용하귀 위해 입력. --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <sec:csrfMetaTags/>
    <title>Login</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script>
        $(document).ready(function () {
        	
        	/* const token = $("meta[name='_csrf']").attr("content");
        	const header = $("meta[name='_csrf_header']").attr("content");
        	const name = $("#userName").val();
        	
        	$.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
            $('#loginForm').submit(function (event) {
                event.preventDefault(); // 폼 submit 이벤트를 막습니다.

                var memberId = $('#memberId').val();
                var memberPassword = $('#memberPassword').val();

                $.ajax({
                    type: 'POST',
                    url: '../member/check', // 컨트롤러의 URL을 정확히 지정해야 합니다.
                    contentType: 'application/json; charset=UTF-8',
                    data: JSON.stringify({
                        memberId: memberId,
                        memberPassword: memberPassword
                    }),
                    success: function (result) {
                        if (result === 1) {
                        	alert(memberId + '님 환영합니다.');
                        } else {
                            alert('로그인 실패. 다시 시도해주세요.');
                        }
                    },
                    error: function () {
                        alert('서버 오류로 인해 로그인을 처리할 수 없습니다.');
                    }
                });
            }); */
        });
    </script>
</head>
<body>
    <h2>로그인</h2>
    <form id="loginForm" method="post" action="member/check">
        <label for="memberId">아이디:</label>
        <input type="text" id="memberId" name="memberId" required><br><br>
        
        <label for="memberPassword">비밀번호:</label>
        <input type="password" id="memberPassword" name="memberPassword" required><br><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="로그인">
    </form>
</body>
</html>
