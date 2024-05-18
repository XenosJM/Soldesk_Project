<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Find ID or Change Password</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	width: 300px;
}

.container h2 {
	margin-top: 0;
	text-align: center;
	color: #333;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	color: #333;
}

.form-group input, .form-group select {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 4px;
}

.form-group input:focus, .form-group select:focus {
	border-color: #007bff;
}

.form-group button {
	width: 100%;
	padding: 10px;
	border: none;
	background-color: #007bff;
	color: white;
	border-radius: 4px;
	cursor: pointer;
	display : none;
}

.backward button {
	width: 100%;
	padding: 10px;
	border: none;
	background-color: #007bff;
	color: white;
	border-radius: 4px;
	cursor: pointer;
}

.form-group button:hover {
	background-color: #0056b3;
}

.email-group {
	display: none;
}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>
	$(document).ready(function() {
		let idFlag = false;
		let pwFlag = false;
		let emailFlag = false;
		let pwConfirmFlag = false;
		let emailAuthFlag = false;
		let checkAuthCode;
		
		// TODO 이메일 보내기 버튼 클릭시 이메일 안가는데 html에 memberEmail이 없어서 값을 못받아서 그럼 id값을 조정하면 쉽게 끝남
		// 아이디를 찾는 버튼은 또 다른 버튼을 만들어야하고, 컨트롤러에서 새로 만들어야함.
		
		// 나중에 그냥 클릭 할떄마다 새로 쓰도록 변경 예정
		$('#optionSelect').change(function() {
			let selectedOption = $(this).val();
			if (selectedOption === 'findId') {
				$('#emailGroupId').show();
				$('#emailGroupPw').hide();
				$('#btnSendId').show();
				$('#btnSendCode').hide();
				$('#btnFormSubmit').hide();
			} else if (selectedOption === 'changePassword') {
				$('#emailGroupPw').show();
				$('#emailGroupId').hide();
				$('#btnSendId').hide();
				$('#btnSendCode').show();
				$('#btnFormSubmit').show();
			} else {
				$('#btnSendCode').hide();
				$('#btnFormSubmit').hide();
				$('#btnSendId').hide();
				$('#emailGroupPw').hide();
				$('#emailGroupId').hide();
			}
		});
		
		$('.memberEmail').each(function() {
			
			let elementId = $(this).attr('id');
			$(document).on('blur', '#' + elementId, function() {
				if (elementId == 'memberId') {
					let memberId = $('#'+elementId).val();
					if (memberId === "") {
						$('#idMsg').html('비밀번호 변경을 위해 반드시 입력해 주세요.');
						$('#idMsg').css('color', 'red');
						idFlag = false;
					} else {
						let memberId = $('#memberId').val();
						checkId(memberId);
					}
				} // end 아이디 체크
				else if(elementId == 'memberEmailForId'){
					let memberEmail = $('#' + elementId).val();
					let emailExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;
					if (memberEmail === "") {
						$('#emailAuthMsgForId').html('이메일은 반드시 적어야합니다.');
						$('#emailAuthMsgForId').css('color', 'red');
						emailFlag = false;
						return;
					}
					if(!emailExp.test(memberEmail)){
						$('#emailAuthMsgForId').html('비 정상적인 이메일입니다.');
						$('#emailAuthMsgForId').css('color', 'red');
						emailFlag = false;
					} else {
						$('#emailAuthMsgForId').html('정상적인 이메일 입니다. 메일 보내기를 눌러 코드를 받아주세요.');
						$('#emailAuthMsgForId').css('color', 'green');
						emailFlag = true;
					}
				} // end ForIdEmail 체크
				else if(elementId == 'memberEmailForPw'){
					let memberEmail = $('#' + elementId).val();
					let emailExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;
					if (memberEmail === "") {
						$('#emailAuthMsgForPw').html('이메일은 반드시 적어야합니다.');
						$('#emailAuthMsgForPw').css('color', 'red');
						emailFlag = false;
						return;
					} 
					if(!emailExp.test(memberEmail)){
						$('#emailAuthMsgForPw').html('이메일은 정상적이어야 합니다.');
						$('#emailAuthMsgForPw').css('color', 'red');
						emailFlag = false;
					} else{
						$('#emailAuthMsgForPw').html('이메일 입니다. 메일 보내기를 눌러 코드를 받아주세요.');
						$('#emailAuthMsgForPw').css('color', 'green');
						emailFlag = true;
					}
				} // end ForPwEmail 체크
				else if(elementId == 'memberPw'){
					let memberPw = $('#' + elementId).val();
					let pwRegExp = /^(?=.*?[A-Z])(?=.?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*]).{8,16}$/;

					if (memberPw === "") {
						$('#pwMsg').html('비밀번호는 필수에오. 필수!');
						$('#pwMsg').css('color', 'red');
						pwFlag = false;
						return;
					}
					/* TODO 아이디를 가져올때 비밀번호도 가져와서 기존비밀번호는 사용 못하도록 할지 고민해 볼 것. 
					else if (memberPw === ){												
					} */
					if (!pwRegExp.test(memberPw)) {
						$('#pwMsg').html('소문자, 대문자, 숫자, 특수문자(!@#$%^&*)중 최소 하나씩을 포함한 8 에서 16 자리만 가능합니다.');
						$('#pwMsg').css('color', 'red');
						pwFlag = false;
					} else {
						$('#pwMsg').html('사용가능한 비밀번호 임미다.');
						$('#pwMsg').css('color', 'green');
						pwFlag = true;
					}
				} // end memberPw 체크
				else if(elementId == 'pwConfirm'){
					let pwConfirm = $('#' + elementId).val();
					
					if (pwConfirm === "") {
						$('#pwConfirmMsg').html('비밀번호 확인은 필수에오.');
						$('#pwConfirmMsg').css('color', 'red');
						pwConfirmFlag = false;
						return;
					}
					if (!memberPw === pwConfirm) {
						$('#pwConfirmMsg').html('저런! 비밀번호를 다시 한번확인해 주세요');
						$('#pwConfirmMsg').css('color',	'red');
						pwConfirmFlag = false;
					} else {
						$('#pwConfirmMsg').html('비밀번호 확인을 통과했습니다.');
						$('#pwConfirmMsg').css('color',	'green');
						pwConfirmFlag = true;
					}
				} // end pwConfirm 체크
			}); // end document.blur
		}); // end document.each
		
		$(document).on('blur', '#authCode', function(){
			
			let authCode = $('#authCode').val();
			if(authCode === ""){
				$('#checkAuthMsg').html('인증코드는 반드시 입력 하셔야합니다!!');
				$('#checkAuthMsg').css('color', 'red');
				emailAuthFlag = false;
			} else{
				$('#checkAuthMsg').html('인증버튼을 눌러주세요.');
				$('#checkAuthMsg').css('color', 'blue');
				emailAuthFlag = false;
			}
			
		}); // end authCode 체크
		
		$(document).on('click', '#btnSendId', function(event) {
			event.preventDefault(); // 기본 동작 막기.
			if(!emailFlag){
				alert('이메일 주소를 확인해 주세요.');
			} else {
				authCodeId($('#memberEmailForId').val()); // 메일 발송
			}
		}); // end 생성될 버튼 정의

		// 생성되는 버튼에 대한 이벤트 처리
		$(document).on('click', '#btnSendCode', function(event) {
			event.preventDefault(); // 기본 동작 막기
			if(!emailFlag){
				alert('이메일 주소를 확인해 주세요.');
			} else {
				let memberEmail = $('#memberEmailForPw').val();
				authCodeSend(memberEmail);
				// alert('이메일 발송이 완료되었습니다.');
			}
		}); // end 생성될 버튼 정의
		
		function authCodeId(memberEmail) {
			//memberEmail = $('#memberEmailForId').val();
			$.ajax({
				type : "GET",
				url : "../util/authCodeId/",
				data : {
					memberEmail : memberEmail
				},
				success : function(result) {
					if (result == 1) {
						alert("작성하신 이메일로 확인 코드가 발송되었습니다.");
					} else {
						alert("이메일을 다시 한번 확인해 주세요.");
					}
				}
			}); // end ajax
		} // end authCodeSend()
		
		function checkId(memberId){
			  		$.ajax({
			  			type : "GET",
			  			url : "../util/checkId/" + memberId,
			  			success : function(result){
			  				if(result == 1){
			  					$('#idMsg').html('회원님의 아이디가 맞습니다.');
			  					$('#idMsg').css('color', 'green');
			  					idFlag = true;
			  				} else {
			  					$('#idMsg').html('회원이 아닌 아이디입니다.');
			  					$('#idMsg').css('color', 'red');
			  					idFlag = false;
			  				}
			  			}
			  		}); // end ajax
			  	} // end checkId
		
		function authCodeSend(memberEmail) {
			  		
			if(idFlag && emailFlag){
				$.ajax({
					type : "GET",
					url : "../util/authCodeSend/",
					data : {
						memberEmail : memberEmail
					},
					success : function(response) {
						if (response.result == 1) {
							
							checkAuthCode = response.authCode;
							alert('성공적으로 메일이 보내졌습니다. 확인해 주세요.');
							$('#emailAuthMsgForPw').html(
									"<input id='authCode' type='number' class='memberEmail' placeholder='코드를 입력해 주세요.'>" +
									"<br><button id='btnCodeCheck' style='display : 'block''>인증확인</button><br><span id='checkAuthMsg'></span>");
							$('#btnCodeCheck').show();
						} else {
							alert("잠시후 다시 눌러주세요.");
						}
					}
				}); // end ajax
			} else{
				alert('뭔갈 안하셧습니다.');
			}
		} // end authCodeSend()
		

		$(document).on('click', '#btnCodeCheck', function(event) {
			event.preventDefault(); // 기본 동작 막기.
			let authCode = $('#authCode').val();
			if (checkAuthCode == authCode) {
				alert('인증에 성공하셨습니다.');
				$('#checkAuthMsg').html(
									'<h3 class="join-title">'
									+ '<label for="memberPw">비밀번호</label>'
									+ '</h3>'
									+ '<input id="memberPw" class ="memberEmail" type="password" name="memberPw" title="비밀번호" maxlength="20">'
									+ '<br>'
									+ '<span id="pwMsg"></span>'
									+ '<h3 class="join-title">'
									+ '<label for="pwConfirm">비밀번호 재확인</label>'
									+ '</h3>'
									+ '<input id="pwConfirm" class ="memberEmail" type="password" name="pwConfirm" title="비밀번호 확인" maxlength="20">'
									+ '<br>'
									+ '<span id="pwConfirmMsg"></span>');
			} else {
				alert('인증에 실패하였습니다. 다시 한번 확인해 주세요.');
				$('#checkAuthMsg').html('인증코드를 확인해 주세요.');
				$('#checkAuthMsg').css('color', 'red');
				emailAuthFlag = false;
			}
		});
		
// TODO 비밀번호 블러처리도 위에서 따로 빼서 진행하고 제출을 누르면 업데이트로 가도록 할것.
		$('#btnFormSubmit').click(function() {

			setTimeout(function() {
				if (emailFlag) {
					$('#btnFormSubmit').submit();
					alert("");
				} else if (idFlag && codeFlag) {
					$('#btnFormSubmit').submit();
					alert("뭔갈 안하셧어요");
				}
			}, 100); // 1초 후에 실행
		});

		$('#btnBackward').click(function(event) {
			event.preventDefault();
			window.location.href = '/ex01/';
		});

		// 비동기로 코드 체크후 통과하면 아이디는 다시 한번 메일 발송후 성공하면 메인으로 넘기기
		// 비밀번호 변경은 아이디체크와 이메일 코드 체크를 통과하면 변경할 비밀번호 입력창과 비밀번호 확인칸을 포함한
		// 폼을 추가후 비밀번호 유효성 검사와 확인을 통과시 제출 버튼 활성화시키고 누르면 변경 완료

	}); // end document
</script>
</head>
<body>
	<div class="container">
		<h2>아이디 찾기/비밀번호 변경</h2>
		<form action="" method="post">
			<div class="form-group">
				<label for="optionSelect">선택지 목록:</label>
				<select id="optionSelect" name="optionSelect" required>
					<option value="">--선택지를 골라주세요--</option>
					<option value="findId">아이디 찾기</option>
					<option value="changePassword">비밀번호 변경</option>
				</select>
			</div>
			<div id="emailGroupId" class="form-group email-group">
				<label for="memberEmailForId">이메일 주소:</label>
				<input type="email" id="memberEmailForId" name="memberEmail" class="memberEmail" value="" required>
				<span id="emailAuthMsgForId" class="memberEmail"></span>
			</div>
			<div id="emailGroupPw" class="form-group email-group">
				<label for="memberId">사용자 아이디:</label>
				<input type="text" id="memberId" name="memberId" class="memberEmail" value="" required>
				<span id="idMsg"></span>
				<label for="memberEmailForPw">이메일 주소:</label>
				<input type="email" id="memberEmailForPw" name="memberEmail" class="memberEmail" value="" required>
				<span id="emailAuthMsgForPw" class="memberEmail"></span>
			</div>
			<div id="btnZone" class="form-group">
				<span id="btnSpan"></span>
				<button id="btnSendId"> 메일 보내기</button>
				<br>
				<button id="btnSendCode"> 메일 보내기</button>
				<br>
				<button id="btnFormSubmit" >제출</button>
			</div>
		</form>
		<div class="backward">
			<button id="btnBackward">뒤로가기</button>
		</div>
	</div>
</body>
</html>





