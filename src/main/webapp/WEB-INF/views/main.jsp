<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 페이지</title>
    <style>
        /* styles.css */
        @font-face {
            font-family: 'Pretendard-Regular';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
            font-weight: 400;
            font-style: normal;
        }

        body {
            font-family: 'Pretendard-Regular';
            margin: 0;
            padding: 0;
        }

        .vertical-header {
            position: fixed;
            left: 0;
            top: 0;
            width: 300px;
            height: 100%;
            background-color: #153f93;
            color: white;
            padding-top: 20px;
            text-align: center;
        }

        .vertical-header .momenu, .menu, .mologo, .mobanner2, .mobanner3, .box, .box2 {
            display: none;
        }

        .vertical-header .logo {
            display: block;
            margin: auto;
            max-width: 200px;
            padding-bottom: 20px;
        }

        .login-container {
            width: 90%;
            margin: 0 auto;
            padding-top: 15px;
            padding-bottom: 15px;
            background-color: #0d2f74;
            border-radius: 10px;
        }

        .login-container h2 {
            text-align: left;
            font-size: 20px;
            margin-left: 20px;
            margin-bottom: 15px;
        }

        .login-container input[type="text"], .login-container input[type="password"], .login-container input[type="submit"] {
            width: 90%;
            margin: 10px auto;
            display: block;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .login-container input[type="submit"] {
            background-color: #153f93;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .login-container p {
            text-align: center;
            margin-top: 15px;
            font-size: 15px;
        }

        .login-container p a {
            color: #FFFFFF;
            text-decoration: none;
            margin: 25px;
        }

        .vertical-header nav {
            padding-top: 10px;
            overflow: hidden;
        }

        .vertical-header nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .vertical-header nav ul li {
            margin: 15px 0;
            padding: 10px 20px;
        }

        .vertical-header nav ul li a {
            color: white;
            text-decoration: none;
            font-size: 17px;
            display: flex;
            align-items: center;
        }

        .vertical-header nav ul li img {
            width: 32px;
            height: 32px;
            margin-right: 10px;
        }

        .vertical-header nav ul li:hover {
            background-color: #0d2f74;
            color: #ffffff;
        }

       .frame-container {
		    display: none;
		    position: fixed;
		    bottom: 20px; /* 화면 하단으로부터 20px 떨어진 위치 */
		    right: 20px;
		    width: 20%;
		    height: 40%;
		    border: 1px solid #000;
		    background: #fff;
		}

        iframe {
            width: 100%;
            height: 90%; /* Close 버튼 공간을 위해 높이 조정 */
            border: none;
        }
       
    </style>
</head>
<body>

    <!-- 사이드 메뉴 -->
    <div class="vertical-header">
        <div class="logo">
        </div>
        <div class="login-container" id="loginContainer"></div>

        <nav>
            <ul>
                <li><a href="/ex01/board/list">전체게시판</a></li>
            </ul>
        </nav>
    </div>

   

    <!-- iframe을 포함하는 컨테이너 -->
    <div class="frame-container" id="frameContainer">
    	<iframe id="myIframe"></iframe>
	</div>

    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script type="text/javascript">
        $(function () {
            if (${empty sessionScope.memberId}) {
                $('#loginContainer').html(
                    '<h2>로그인</h2>'
                    + '<input type="text" id="memberId" name="memberId" placeholder="아이디">'
                    + '<input type="password" id="memberPassword" name="memberPassword" placeholder="비밀번호"><br>'
                    + '<button id="check">로그인</button>'
                    + '<button id="joinMember">회원가입</button>'
                    + '<button id="findIdPw">ID/PW찾기</button>'
                );
            } else {
                $('#loginContainer').html(
                    '<h3>${sessionScope.memberId}</h3>'
                    + '<button id="detail">내 정보 보기</button>'
                    + '<button id="checkout">로그아웃</button>'
                    + '<button id="friendList">친구목록보기</button>'
                );
            }

            $(document).on('click', '#check', function () {
                let memberId = $('#memberId').val();
                let memberPassword = $('#memberPassword').val();
                $.ajax({
                    type: 'POST',
                    url: 'member/check',
                    contentType: 'application/json; charset=UTF-8',
                    data: JSON.stringify({
                        memberId: memberId,
                        memberPassword: memberPassword
                    }),
                    success: function (result) {
                        if (result == 1) {
                            alert(memberId + '님 어서오세요.');
                            $('#loginContainer').html(
                                '<h3>' + memberId + '</h3>'
                                + '<button id="detail">내 정보 보기</button>'
                                + '<button id="checkout">로그아웃</button>'
                                + '<button id="friendList">친구목록보기</button>'
                            );
                        } else {
                            alert('입력하신 정보를 다시한번 확인해주세요.');
                        }
                    }
                }); // end ajax
            });

            $(document).on('click', '#joinMember', function () {
                window.location.href = "member/regist";
            });

            $(document).on('click', '#findIdPw', function () {
                window.location.href = "member/findIdPw";
            });

            $(document).on('click', '#detail', function () {
                window.location.href = "member/detail";
            });

            $(document).on('click', '#checkout', function () {
                window.location.href = "member/checkout";
            });

        });

        // iframe에 관련된 함수와 변수명 정의
         $(document).on('click', '#friendList', function() {
            let frameContainer = $('#frameContainer');
            if (frameContainer.css('display') === 'none' || frameContainer.css('display') === '') {
            	frameContainer.css('display', 'block');
            	$('#loginContainer').find('#friendList').remove();
            	$('#loginContainer').append('<button id="friendListClose">친구목록 닫기</button>')
                $('#myIframe').attr('src', 'member/friendList'); // 불러올 JSP 파일의 경로 설정
            } else {
            	
            }
        });
        
        $(document).on('click', '#friendListClose', function(){
        	let frameContainer = $('#frameContainer');
        	frameContainer.css('display', 'none');
        	$('#loginContainer').find('#friendListClose').remove();
        	$('#loginContainer').append('<button id="friendList">친구목록보기</button>')
            $('#myIframe').attr('src', 'about:blank');
        });
        
    </script>
</body>
</html>





