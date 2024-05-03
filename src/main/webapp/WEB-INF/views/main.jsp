<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 페이지</title>
    <style>
    	<style type="text/css">
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

@media screen and (min-width: 1280px) {


.vertical-header {
    position: fixed;
    left: 0;
    top: 0;
    width: 300px; /* 헤더의 너비 */
    height: 100%; /* 헤더의 높이, 화면 전체 */
    background-color: #153f93; /* 배경색상은 여기서는 검은색으로 지정 */
    color: white;
    padding-top: 20px; /* 로고와 메뉴 사이의 간격 */
    text-align: center;  /* 수평 가운데 정렬 */
}

.vertical-header .momenu,.menu,.mologo,.mobanner2,.mobanner3,.box, .box2 {
    display: none;
}

.vertical-header .logo {
    display: block; /* 인라인 요소를 블록 요소로 변환 */
    margin: auto; /* 수직 가운데 정렬 */
}

.login-container {
    width: 300px;
    margin: 0 auto;
    padding-top: 15px;
    padding-bottom: 15px;
    background-color: #0d2f74;
    }

.login-container h2 {
    text-align: left;
    font-size:20px;
    margin-left: 50px;
    margin-bottom: 15px;
}

.login-container input[type="text"],
.login-container input[type="password"] {
    width: 200px;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
}

.login-container input[type="submit"] {
    width:200px;
    padding: 10px;
    background-color: #153f93;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    }

.login-container p {
    text-align: center;
    margin-top: 15px;
    font-size:15px;
}

.login-container p a {
    color: #FFFFFF;
    text-decoration: none;
    margin : 25px;
}


.vertical-header nav {
    padding-top: 10px; /* 맨 위에서 10px 내려서 시작 */
    overflow: hidden;
}

.vertical-header nav ul {
    list-style: none;
    padding: 0;
    }

.vertical-header nav ul li {
    margin: 15px 0;
    padding: 10px 50px;
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
    margin-right: 30px; /* 텍스트와 이미지 사이의 간격 */
}


.vertical-header nav ul li:hover {
    background-color:#0d2f74;
    color: #ffffff;
}

#Wrap {position:relative; margin: 20px 30px 0 330px;}
#Wrap img {width:100%;}

.banner {
    height: 250px;
    text-align: center;
   }

.banner img {
    height: 250px;
    object-fit:cover;
    border-radius: 15px;
}

.mobanner {
    display: none;
}

.banner2 {
    position:relative;
     margin:30px 0;
    }

.banner2 .title {
    margin: 20px auto;
    width: 40%;
}

.banner2 ul {
    display: flex;
    list-style:none;
    width: 100%;
    padding: 0;
}

.banner2 ul li {
    padding-right: 15px;
    width: 25%;
}

.banner2 ul li:nth-child(4) {
    padding-right:0;
}

 .banner3 {
  margin-top: 20px;
}


.banner4 {
    margin-top: 30px;
  }

  .banner4 ul {
    display: flex;
    list-style:none;
    width: 100%;
    padding: 0;
}

.banner4 ul li {
    padding-right: 15px;
    width: 50%;
}

.banner4 ul li:nth-child(2) {
    padding-right:0;
}


.line {
    margin: 30px 30px 0 330px;
}

.line hr {
    border-top: 1px solid #b3b2c1; /
    }

.line p {
    text-align: center;
    font-size: 12px;
    color: #444444;
}
}

@media screen and (max-width: 1279px) {
    /* 모바일 화면에 최적화된 스타일을 추가합니다 */
    .vertical-header {
    position: relative;    
    width: 100%;
    background-color: #153f93;
    margin: 0;
    height: 56px;
    }

    .momenu {
        display: block;
        width: 30px;
        height: 30px;
        cursor: pointer;
        position: fixed;
        top: 20px;
        left: 20px;
        z-index: 1000;
    }

    .bar {
        width: 100%;
        height: 3px;
        background-color: #333;
        margin: 5px 0;
    }

    .menu {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.8);
        z-index: 999;
    }

    .menu ul {
        list-style: none;
        padding: 0;
        margin: 0;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .menu ul li {
        margin-bottom: 20px;
    }

    .menu ul li a {
        color: #fff;
        text-decoration: none;
        font-size: 20px;
    }

    .close-btn {
        position: absolute;
        top: 20px;
        right: 20px;
        color: #fff;
        font-size: 24px;
        cursor: pointer;
    }

    .vertical-header .momenu img {
        width: 25px;
    }
    
    .vertical-header .mologo img {
      display: block;
      margin: 0 auto;
      padding-top: 15px;
      }

    .mobanner img {
        width: 100%;
        }
    
  
   .login-container,.vertical-header nav,.logo,.banner,.banner2,.banner3,.banner4 { 
    display: none;
    }

    .mobanner2 {
        position:relative;
         margin:20px 0;
        }
    
    .mobanner2 .title {
        margin: 0 auto;
        width: 80%;
    }

    .mobanner2 .title img {
        margin: 0 auto;
        width: 100%;
    }

    .mobanner2 ul {
        list-style:none;
        width: 100%;
        padding: 0;
    }
    
    .mobanner2 ul li {
        padding: 2px 0;
    }

    .mobanner2 ul li img {
       width: 100%;
    }
    
    .mobanner2 ul li:nth-child(4) {
        padding-bottom:0;
    }

    .mobanner3 img {
        width: 100%;
    }


    .box {
    position:relative; 
    margin:24px 0; 
    box-sizing:border-box;
    width:100%; 
    background:#e3efff; 
    padding:24px;
    border-radius:10px;
    }

    .box strong {
    font-weight:800; 
    font-size:20px; 
    color:#0a69df;
    }

    .box .table_area {
        position:relative;
         margin:22px 0; 
         background:#e3efff; 
         overflow-x: scroll;
          color:#e3efff; 
          border-radius:10px;
     }

   .box .table {
    position:relative; 
    width:150%; 
    padding:20px;
 }

 .box .table img{
    width: 150%;
 }

   .box ul {
    font-size:14px; 
    color:#555555; 
    line-height:22px; 
    padding-left:16px; 
}

.box2 {
    position:relative; 
    margin:24px 0; 
    box-sizing:border-box;
    width:100%; 
    background:#e8edff; 
    padding:24px;
    border-radius:10px;
    }

    .box2 strong {
    font-weight:800; 
    font-size:20px; 
    color:#0a69df;
    }

    .box2 .table_area {
        position:relative;
         margin:22px 0; 
         background:#e8edff; 
         overflow-x: scroll;
        border-radius:10px;
     }

   .box2 .table {
    position:relative; 
    width:150%; 
    padding:20px;
 }

 .box2 .table img{
    width: 150%;
 }

   .box2 ul {
    font-size:14px; 
    color:#555555; 
    line-height:22px; 
    padding-left:16px; 
}

    
    .line {
        margin: 30px 0;
    }

    .line p {
        text-align: center;
        font-size: 12px;
        color: #444444;
    }
    
  }
    </style>
    
</head>
<body>
    <div class="vertical-header">
    
    <nav class="menu" id="menu">
        <span class="close-btn" onclick="toggleMenu()">×</span>
        <ul>
            <li><a href="#">메뉴 항목 1</a></li>
            <li><a href="#">메뉴 항목 2</a></li>
            <li><a href="#">메뉴 항목 3</a></li>
        </ul>
    </nav>
      
        <div class="logo">
          <a href="#"><img src="images/logo.png" alt="랩탑로고"> <!-- 로고 이미지 --></a>
        </div>
      
        <div class="login-container">
          <h2>로그인</h2>
          <form action="#" method="post">
            <input type="text" name="username" placeholder="아이디">
            <input type="password" name="password" placeholder="비밀번호">
            <input type="submit" value="로그인">
          </form>
          <p><a href="../regist.jsp">회원가입</a> | <a href="../memberFind.jsp">ID/PW찾기</a></p>
        </div>
      
        <nav>
          <ul>
            <li><a href="#"><img src="" alt=""> 자유게시판</a></li>
            <li><a href="#"><img src="" alt=""> 리버스1999</a></li>
            <li><a href="#"><img src="" alt=""> 붕괴스타레일</a></li>
            <li><a href="#"><img src="" alt=""> 니케</a></li>
            <li><a href="#"><img src="" alt=""> 마작일번가</a></li>
          </ul>
        </nav>
      </div>
      
      
      <div id ="Wrap">
        <div class="banner">
          <a href="../regist.jsp"><img src="images/banner1.png" alt="회원가입 유도 배너"></a>
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

      
        <div class="banner3">
         <a href="#"><img src="images/banner3.png" alt="공지사항"></a>
        </div>

        <div class="banner4">
          <ul>
            <li><a><img src="images/banner4.png" alt="banner4"></a></li>
            <li><a><img src="images/banner5.png" alt="banner4"></a></li>
          </ul>
        </div>

        <div class="box">
          <strong class="title">
            이벤트 배너
          </strong>
          <div class="table_area">
            <div class="table">
              <a><img src="" ></a>
            </div>
          </div>
          <ul>
            <li>설명2</li>
            <li>설명3</li>
            <li>설명4</li>
            <li>설명5</li>
          </ul>
        </div>

        <div class="box2">
          <strong class="title">
            이벤트 배너
          </strong>
          <div class="table_area">
            <div class="table">
              <a><img src="" ></a>
            </div>
          </div>
          <ul>
            <li>설명1</li>
            <li>설명1</li>
            <li>설명1</li>
            <li>설명1</li>
          </ul>
        </div>

      </div>

      <div class="line">
        <hr>
        <p>Copyright (C) 내가 DB가 될게 All Rights Reserved.</p>
      </div>

      <script>
        function toggleMenu() {
            var menu = document.getElementById('menu');
            if (menu.style.display === 'block') {
                menu.style.display = 'none';
            } else {
                menu.style.display = 'block';
            }
        }
    </script>
    
>>>>>>> 4d605e5fa1ebb31e5e2e9d38b55d46f1c4ef5ad9
</body>


</html>