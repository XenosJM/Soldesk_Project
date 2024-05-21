<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<style>
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
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<body>
	<h2>회원 가입 페이지</h2>
	<div class="form-Wrapper">
	  <form id="joinForm" action="regist" method="POST">
	    <div class="join-content">
	      <div class="row-group">
	        <div class="join-row">
	          <h3 class="join-title">
	            <label for="memberId">아이디</label>
	          </h3>
	          <span>
	            <input id="memberId" class ="memberInfo" type="text" name="memberId" title="아이디" maxlength="15">
	            <br>
	          </span>
	          <span id="idMsg"></span>
	        </div>
	        
	        <div class="join-row">
	          <h3 class="join-title">
	            <label for="memberPassword">비밀번호</label>
	          </h3>
	          <span>
	            <input id="memberPassword" class ="memberInfo" type="password" name="memberPassword" title="비밀번호" maxlength="20">
	            <br>
	          </span>
	          <span id="pwMsg"></span>
	          
	          <h3 class="join-title">
	            <label for="passwordConfirm">비밀번호 재확인</label>
	          </h3>
	          <span>
	            <input id="passwordConfirm" class ="memberInfo" type="password" name="passwordConfirm" title="비밀번호 확인" maxlength="20">
	            <br>
	          </span>
	          <span id="pwConfirmMsg"></span>
	          
	          <h3 class="join-title">
	          	<label for="memberEmail">이메일</label>
	          </h3>
	          <span>
	            <input id="memberEmail" class ="memberInfo" type="email" name="memberEmail" title="이메일" maxlength="30">
	            <br>
	          </span>
	          <span id="emailMsg"></span>
	          
	          <!-- <span>
	            <button id="btnEmailAuth">이메일 인증하기</button>
	            <br>
	          </span>
	          <span id="emailAuthMsg"></span> -->
	        </div>
	      </div>
	      <!-- 스프링 시큐리티를 사용하면 모든 post 전송에 csrf 토큰을 추가해야 함 -->
	      <!-- <input type="hidden" name="_csrf" value="7d745488-7ec9-4937-9c2d-b58285bc9676"> -->
	    </div>  
	  </form>
	  <span id="authSpan"></span>
  </div>
	      <hr>
  
 	 <button id="btnJoin">제출</button>
<script type="text/javascript">
  	$(document).ready(function(){

  		let idFlag = false; // memberName 유효성 변수 
  		let pwFlag = false; // memberPassword 유효성 변수 
  		let pwConfirmFlag = false; // pwConfirm 유효성 변수 
  		let emailFlag = false; // memberEmail 유효성 변수
  		let emailAuthFlag = false; // 실제 있는 이메일인지 확인하는 변수
  		let checkAuthCode; // 전역변수로 선언

		// memberInfo 클래스를 가진 요소 찾기	  		
	  	$('.memberInfo').each(function(){
	  		// 각 요소 id값 가져옴
	  		let elementId = $(this).attr('id');
	  		// blur() : input 태그에서 탭 키나 마우스로 다른 곳을 클릭할 때 이벤트 발생
	  		// 아이디 유효성 검사	 
	  		$('#' + elementId).blur(function(){
	  			
	  			if(elementId == 'memberId') {
	  				let memberId = $('#' + elementId).val();
	  				
	  			  	// 7 ~ 20자 사이의 소문자나 숫자로 시작하고, 소문자, 숫자을 포함하는 정규표현식
	  				let idRegExp = /^[a-z0-9][a-zA-Z0-9]{6,19}$/;
	  				if(memberId === ""){
	  					$('#idMsg').html("아이디는 비어둘 수 없습니다.");
	  					$('#idMsg').css("color", "red");
	  					idFlag = false;
	  					return;
	  				}
	  				
	  				if(!idRegExp.test(memberId)){
	  					$('#idMsg').html("아이디는 7-20자 사이로 영어, 숫자만 입력이 가능 합니다.");
	  					$('#idMsg').css("color", "red");
	  					idFlag = false;
	  				} else {
	  					checkId(memberId);
	  				}
	  				
	  			} // end 아이디 요소 유효성 검사
	  			else if (elementId == 'memberPassword') {
	  				let memberPw = $('#' + elementId).val();
	  				let pwRegExp = /^(?=.*?[A-Z])(?=.?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*]).{8,16}$/;
	  				
	  				if(memberPw === ""){
	  					$('#pwMsg').html("비밀번호는 필수에오. 필수!");
	  					$('#pwMsg').css("color", "red");
	  					pwFlag = false;
	  					return;
	  				}
	  				if(!pwRegExp.test(memberPw)){
	  					$('#pwMsg').html("소문자, 대문자, 숫자, 특수문자(!@#$%^&*)중 최소 하나씩을 포함한 8 에서 16 자리만 가능합니다.");
	  					$('#pwMsg').css("color", "red");
	  					pwFlag = false;
	  				} else {
	  					$('#pwMsg').html("사용가능한 비밀번호 임미다.");
	  					$('#pwMsg').css("color", "green");
	  					pwFlag = true;
	  				}
	  			} // end 비밀번호 유효성 검사
	  			else if (elementId == 'passwordConfirm') {
	  				let pwConfirm = $('#' + elementId).val();
	  				let memberPw = $('#memberPassword').val();
	  				
	  				if(pwConfirm === ""){
	  					$('#pwConfirmMsg').html("비밀번호 확인은 필수에오.");
	  					$('#pwConfirmMsg').css("color", "red");
	  					pwConfirmFlag = false;
	  					return;
	  				}		
	  				if(memberPw === pwConfirm){
	  					$('#pwConfirmMsg').html("비밀번호 확인을 통과했습니다.");
	  					$('#pwConfirmMsg').css("color", "green");
	  					pwConfirmFlag = true;
	  				} else {
	  					$('#pwConfirmMsg').html("저런! 비밀번호를 다시 한번확인해 주세요");
	  					$('#pwConfirmMsg').css("color", "red");
	  					pwConfirmFlag = false;
	  				}
	  			} // end 비밀번호 확인 유효성 검사
	  			else if (elementId == "memberEmail"){
	  				let memberEmail = $('#' + elementId).val();
	  				let emailRegExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;
	  				// let emailRegExp = /(?:[a-zA-Z0-9._%+-]+@(?:naver\.com|daum\.net|kakao\.com|gmail\.com))/i;
	  				console.log(memberEmail);
	  				if(memberEmail === ""){
	  					$('#emailMsg').html("이메일도 필수에오. 필수!");
	  					$('#emailMsg').css("color", "red");
	  					$('#authSpan').html('');
	  					$('#btnEmailAuth').css("display", "none");
	  					emailFlag = false;
	  					return;
	  				}
	  				if(!emailRegExp.test(memberEmail)){
	  					$('#emailMsg').html("유효한 형식의 이메일이 아니에오");
	  					$('#authSpan').html('');
	  					/* $('#emailMsg').html("유효한 형식의 이메일이 아니에오, naver.com, daum.net, kakao.com, gmail.com <br> 이 4개의 이메일만 사용 가능합니다 "); */
	  					$('#emailMsg').css("color", "red");
	  					$('#btnEmailAuth').css("display", "none");
	  					emailFlag = false;
	  				} else {
			  			checkMail(memberEmail);
		  		  	}
	  			} // end email 체크
	  			
	  		}); // end blur(function);	  		
	  	}); // end each()
	  	
	  	
	  	function checkId(memberId){
	  		
	  		$.ajax({
	  			type : "GET",
	  			url : "../util/checkId/" + memberId,
	  			success : function(result){
	  				if(result == 1){
	  					$('#idMsg').html('이미 누군가가 사용중인 아이디입니다.');
	  					$('#idMsg').css('color', 'red');
	  					idFlag = false;
	  				} else {
	  					$('#idMsg').html('사용가능한 아이디입니다.');
	  					$('#idMsg').css('color', 'green');
	  					idFlag = true;
	  				}
	  			}
	  		}); // end ajax
	  	} // end checkId
	  	
	  	function checkMail(memberEmail){
	  	
	  		$.ajax({
	  			type : "GET",
	  			url : "../util/checkEmail/",
	  			data: { memberEmail : memberEmail },
	  			success : function(result){
	  				if(result == 1){
	  					$('#emailMsg').html("누군가 벌써 사용중인 이메일입니다.");
	  					$('#emailMsg').css("color", "red");
	  					$('#authSpan').html("");
	  					$('#btnEmailAuth').css("display", "none");
	  					emailFlag = false;
	  				} else {
	  					if($('#authSpan').length > 0){
	  						
	  					} else{
	  					$('#emailMsg').html("사용가능한 이메일입니당.");
	  					$('#emailMsg').css("color", "green");
	  					$('#authSpan').html("<span><button id='btnSendCode'>이메일 인증하기</button><br></span><span id='emailAuthMsg'></span>");
	  					// $('#btnEmailAuth').css("display", "block")
	  					emailFlag = true;
	  					}
	  				}
	  			}
	  		});	// end ajax
	  	} // end checkMail
	  	
		$(document).on('click', '#btnSendCode', function(){
			authCodeSend($('#memberEmail').val());
		}); // end btnSendCode
		
		function authCodeSend(memberEmail){
			
			$.ajax({
				type : "GET",
				url : "../util/authCodeSend/",
				data : {memberEmail : memberEmail},
				success : function(response){
					if(response.result === 1){
						alert("작성하신 이메일로 확인 코드가 발송되었습니다.");
						$('#emailAuthMsg').html("<input id='authCode' type='number' placeholder='코드를 입력해 주세요.'><button id='btnCodeCheck'>인증확인</button><br><span id='checkAuthMsg'></span>");
						checkAuthCode = response.authCode;
						console.log(checkAuthCode);
					} else{
						alert("잠시후 다시 눌러주세요.");
					}
				}
			}); // end ajax
		} // end authCodeSend()
		
		$(document).on('click', '#btnCodeCheck', function(){
			codeCheck($('#authCode').val());
		});
		
		$(document).on('blur', '#authCode', function(){
  				let authCode = $('#authCode').val();
  				if(authCode == ""){
  					$('#checkAuthMsg').html('인증코드는 비어있을 수 없어용.');
  					$('#checkAuthMsg').css('color', 'red');
  					emailAuthFlag = false;
  				} else {
  					$('#checkAuthMsg').html('인증코드 확인 버튼을 눌러주세요');
  					$('#checkAuthMsg').css('color', 'blue');
  					emailAuthFlag = false;
  				}
		});
		
		// TODO: 나중에 비동기로 처리를 하던, 하는방식으로 바꿔야함 이대로면 사용자가 많아질경우 값이 계속 바뀔 가능성이 있음.
		// 정확히는 테스트를 해봐야 알거같음.
		function codeCheck(authCode){
			if(authCode == checkAuthCode){
				alert("인증에 성공하셨습니다.");
				$('#checkAuthMsg').html('인증코드가 확인되었습니다.');
				$('#checkAuthMsg').css('color', 'green');
				emailAuthFlag = true;
			} else{
				alert("인증코드를 다시 확인해 주세요.");
				$('#checkAuthMsg').html('인증코드를 확인해 주세요.');
				$('#checkAuthMsg').css('color', 'red');
				emailAuthFlag = false;
			}
		};
		
		
	  	
	  	$('#btnJoin').click(function(){
	  		console.log('idFlag : ' + idFlag);
	  		console.log('pwFlag : ' + pwFlag);
	  		console.log('pwConfirmFlag : ' + pwConfirmFlag);
	  		console.log('emailFlag : ' + emailFlag);
	  		console.log('emailAuthFlag : ' + emailAuthFlag);
	  		setTimeout(function(){
	  			if(idFlag && pwFlag && pwConfirmFlag && emailFlag && emailAuthFlag){
	  				$('#joinForm').submit();
	  				alert($('#memberId').val() + "님의 회원가입을 환영합니다.");
	  			} else {
	  				alert("뭔갈 안하셧어요");
	  			}
	  		}, 500); // 500초 후에 실행
	  	}); // end btnJoin
	  	
  }); // end document

  </script>
</body>
</html>




