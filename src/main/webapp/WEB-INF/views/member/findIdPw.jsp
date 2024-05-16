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
		
		// 이메잃 보내기 버튼 클릭시 이메일 안가는데 html에 memberEmail이 없어서 값을 못받아서 그럼 id값을 조정하면 쉽게 끝남
		// 아이디를 찾는 버튼은 또 다른 버튼을 만들어야하고, 컨트롤러에서 새로 만들어야함.
		
		// 나중에 그냥 비동기로 클릭 할떄마다 새로 쓰도록 변경 예정
		$('#optionSelect').change(function() {
			let selectedOption = $(this).val();
			if (selectedOption === 'findId') {
				$('#emailGroupId').show().val("");
				$('#emailGroupPw').val("").hide();
				$('#btnFormSubmit').hide();
				$('#btnSendCode').show();
			} else if (selectedOption === 'changePassword') {
				$('#emailGroupPw').show().val("");
				$('#emailGroupId').val("").hide();
				$('#btnSendCode').hide();
				$('#btnFormSubmit').show();
			} else {
				$('.email-group').hide();
				$('#btnSendCode').hide();
				$('#btnFormSubmit').hide();
				$('#emailGroupId, #emailGroupPw').val("");
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
					} else {
						$('#idMsg').html('');
					}
				} // end 아이디 체크
				else if(elementId == 'memberEmailForId'){
					let memberEmail = $('#' + elementId).val();
					if (memberEmail === "") {
						$('#emailAuthMsgForId').html('이메일은 반드시 적어야합니다.');
						$('#emailAuthMsgForId').css('color', 'red');
					} else{
						$('#emailAuthMsgForId').html('');
					}
				} // end ForIdEmail 체크
				else if(elementId == 'memberEmailForPw'){
					let memberEmail = $('#' + elementId).val();
					
					if (memberEmail === "") {
						$('#emailAuthMsgForPw').html('이메일은 반드시 적어야합니다.');
						$('#emailAuthMsgForPw').css('color', 'red');
					} else{
						$('#emailAuthMsgForPw').html('');
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
					if (memberPw === pwConfirm) {
						$('#pwConfirmMsg').html('비밀번호 확인을 통과했습니다.');
						$('#pwConfirmMsg').css('color',	'green');
						pwConfirmFlag = true;
					} else {
						$('#pwConfirmMsg').html('저런! 비밀번호를 다시 한번확인해 주세요');
						$('#pwConfirmMsg').css('color',	'red');
						pwConfirmFlag = false;
					}
				} // end pwConfirm 체크
				else if(elementId == 'authCode'){
					let authCode = $('#' + elementId).val();
					if(authCode === ""){
						$('#checkAuthMsg').html('인증코드는 반드시 입력 하셔야합니다!!');
						$('#checkAuthMsg').css('color', 'red');
						emailAuthFlag = false;
					} else{
						$('#checkAuthMsg').html('인증버튼을 눌러주세요.');
						$('#checkAuthMsg').css('color', 'blue');
						emailAuthFlag = false;
					}
				} // end authCode 체크
				
			}); // end document.blur
		}); // end document.each
		

		// 생성되는 버튼에 대한 이벤트 처리
		$(document).on('click', '#btnSendCode', function(event) {
					event.preventDefault(); // 기본 동작 막기.
					// TODO 이메일 플래그를 체크할지 안할지 고민좀 할것.
					authCodeSend($('#memberEmailForId').val()); // 메일 발송
				}); // end 생성될 버튼 정의

		function authCodeSend(memberEmail) {
			let memberId = $('#memberId').val();
			memberEmail = $('#memberEmailForId').val();
			$.ajax({
				type : "GET",
				url : "../util/authCodeSend/",
				data : {
					memberEmail : memberEmail,
					memberId : memberId
				},
				success : function(result) {
					if (result >= 1) {
						alert("작성하신 이메일로 확인 코드가 발송되었습니다.");
						$('#emailAuthMsg').html(
												"<input id='authCode' type='number' class='memberEmail' placeholder='코드를 입력해 주세요.'>"
												+ "<button id='btnCodeCheck'>인증확인</button><br><span id='checkAuthMsg'></span>");
						checkAuthCode = result;
					} else {
						alert("잠시후 다시 눌러주세요.");
					}
				}
			}); // end ajax
		} // end authCodeSend()
		

		$(document).on('click', '#btnCodeCheck', function(event) {
			event.preventDefault(); // 기본 동작 막기.
			let authCode = $('#authCode').val();
			if (checkAuthCode === authCode) {
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
				<span id="emailAuthMsgForId" class="emailAuthMsg"></span>
			</div>
			<div id="emailGroupPw" class="form-group email-group">
				<label for="memberId">사용자 아이디:</label>
				<input type="text" id="memberId" name="memberId" class="memberEmail" value="" required>
				<span id="idMsg"></span>
				<label for="memberEmailForPw">이메일 주소:</label>
				<input type="email" id="memberEmailForPw" name="memberEmail" class="memberEmail" value="" required>
				<span id="emailAuthMsgForPw" class="emailAuthMsg"></span>
			</div>
			<div class="form-group">
				<button id="btnSendCode" style='display: none;'> 메일 보내기</button>
				<br>
				<button id="btnFormSubmit" style='display: none;'>제출</button>
			</div>
		</form>
		<div class="form-group">
			<button id="btnBackward">뒤로가기</button>
		</div>
	</div>
</body>
</html>





