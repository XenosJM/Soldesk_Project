<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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
          width: 300px; /* 사이드 메뉴 너비를 300px로 설정 */
          height: 100%; /* 헤더의 높이, 화면 전체 */
          background-color: #153f93; /* 배경색상은 여기서는 검은색으로 지정 */
          color: white;
          padding-top: 20px; /* 로고와 메뉴 사이의 간격 */
          text-align: center;  /* 수평 가운데 정렬 */
      }

      .vertical-header .momenu, .menu, .mologo, .mobanner2, .mobanner3, .box, .box2 {
          display: none;
      }

      .vertical-header .logo {
          display: block;
          margin: auto;
          max-width: 200px; /* 로고의 최대 너비 지정 */
          padding-bottom: 20px; /* 로고와 메뉴 사이의 간격 */
      }

      .login-container {
          width: 90%; /* 로그인 컨테이너의 너비를 90%로 지정 */
          margin: 0 auto;
          padding-top: 15px;
          padding-bottom: 15px;
          background-color: #0d2f74;
          border-radius: 10px; /* 테두리를 부드럽게 만들기 위해 추가 */
      }

      .login-container h2 {
          text-align: left;
          font-size: 20px;
          margin-left: 20px; /* 왼쪽 여백 축소 */
          margin-bottom: 15px;
      }

      .login-container input[type="text"],
      .login-container input[type="password"],
      .login-container input[type="submit"] {
          width: 90%; /* 입력 필드 및 버튼 너비를 90%로 지정 */
          margin: 10px auto; /* 상하 여백을 중앙으로 정렬 */
          display: block; /* 한 줄에 하나씩 표시 */
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
          margin: 0; /* 추가 */
      }

      .vertical-header nav ul li {
          margin: 15px 0;
          padding: 10px 20px; /* 메뉴 간격을 조정 */
      }

      .vertical-header nav ul li a {
          color: white;
          text-decoration: none;
          font-size: 17px;
          display: flex;
          align-items: center;
      }

      .vertical-header nav ul li img {
          width: 32px; /* 이미지의 너비 */
          height: 32px; /* 이미지의 높이 */
          margin-right: 10px; /* 텍스트와 이미지 사이의 간격 */
      }

      .vertical-header nav ul li:hover {
          background-color: #0d2f74;
          color: #ffffff;
      }
    </style>
    
</head>
<body>
<div class="vertical-header">
    <div class="logo">
        <a href="/ex01/"><img src="images/logo.png" alt="메인로고"></a> <!-- 로고 이미지 -->
    </div>

    <div class="login-container">
        <h2>로그인</h2>
        <form action="#" method="post">
            <input type="text" name="username" placeholder="아이디">
            <input type="password" name="password" placeholder="비밀번호">
            <input type="submit" value="로그인">
        </form>
        <p><a href="#" onclick="window.location.href='/ex01/member/regist'">회원가입</a> | <a href="../memberFind">ID/PW찾기</a></p>
    </div>

    <nav>
        <ul>
            <li><a href="/ex01/board/list"><img src="" alt=""> 전체게시판</a></li>
            <li><a href="/ex01/board/reverse1999"><img src="" alt=""> 리버스1999</a></li>
            <li><a href="/ex01/board/starrail"><img src="" alt=""> 붕괴스타레일</a></li>
            <li><a href="#"><img src="" alt=""> 작혼 : 리치마작</a></li>
            <li><a href="#"><img src="" alt=""> 니케</a></li>
            <li><a href="#"><img src="" alt=""> 마작일번가</a></li>
        </ul>
    </nav>
</div> <!-- 사이드 배너 -->

<div>
    <div id="Wrap">
        <div class="banner">
            <a href="/ex01/member/regist"><img src="images/banner1.png" alt="메인 배너"></a>
        </div>

        <div class="banner2">
            <div class="title">
                <a><img src="images/title.jpg" alt="타이틀"></a>
            </div>
            <ul>
                <li><a><img src="*" alt="info"></a></li>
                <li><a><img src="*" alt="proceeds"></a></li>
                <li><a><img src="*" alt="best"></a></li>
                <li><a><img src="*" alt="statistics"></a></li>
            </ul>
        </div>
    </div>

</div> <!-- 메인 화면 배너들 -->

</body>
</html>
