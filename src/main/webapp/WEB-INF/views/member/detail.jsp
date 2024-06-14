<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<sec:csrfMetaTags/>
 <style>
        .checkbox-container {
            display: flex;
            align-items: center;
            margin-bottom: 5px; /* 각 항목 사이에 약간의 공백을 둡니다 */
        }
        .checkbox-container input {
            margin-right: 10px; /* 체크박스와 텍스트 사이에 공백을 둡니다 */
            margin-left: 10px;
        }
    </style>
<title>회원 정보</title>
</head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<body>
	<div id="mainDetail">
		<h2><c:out value="${memberVO.memberId}"></c:out>님의 정보입니다.</h2>
		<br>
			<div id="emailDiv">
				<c:out value="현재 이메일 : ${memberVO.memberEmail}"></c:out>
			</div>
		<br>
		<div id="passwordDiv"></div>
		
	   	<div>
	   		<div id="propertyDiv">
				<c:choose>
				    <c:when test="${empty memberVO.memberProperty}">
				    	<p>현재 보유중인 이모티콘이 없습니다. 구매하러 가시겠습니까?<!-- //TODO 변경할 예정 -->
				        	<input type="submit" id="buyProperty" onclick="location.href='http://localhost:8080/ex01/shop/imoji'" value="상점">
				        <br>
				    </c:when>
				    <c:when test="${not empty memberVO.memberProperty}">
				        <p>현재 보유중인 상품 목록:</p>
					        <ul>
					            <c:forEach var="property" items="${memberVO.memberProperty}">
					                <li>${property}</li>
					            </c:forEach>
					        </ul>
				        <br>
				    </c:when>
				</c:choose>
	   		</div>	
	  	</div>
		
		<c:out value="회원가입 날짜 : ${memberVO.memberRegistDate }"></c:out>
		<br>
		
		<button id="btnModify" class="btn btn-primary">회원정보 수정</button>
		<button id="btnModifyCancel" class="btn btn-primary">수정 취소</button>
		<button id="btnModifyMember" class="btn btn-primary">전체 수정하기</button>
		<button id="btnBackward" class="btn btn-primary">뒤로가기</button>
		<button id="btnDelete" class="btn btn-primary">탈퇴하기</button>
	</div>
	
	<div class="modal" id="checkModal">
    	<div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title">비밀번호 확인</h4>
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	            </div>
	            <div class="modal-body">
           			<input type="password" class="form-control" id="memberPassword" placeholder="비밀번호를 입력하세요.">
    				<button type="button" class="close" id="btnPasswordCheck">비밀번호 확인</button>
    				<span id="modalCheckMsg"></span>
	            </div>
        	</div>
       	</div>
   	</div>
   	
   	<div class="modal" id="deleteModal">
    	<div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title">회원 탈퇴 확인</h4>
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	            </div>
	            <div class="modal-body">
    				<span id="deleteMsg"></span>
	            </div>
        	</div>
       	</div>
   	</div>
	        	
	
	<script type="text/javascript">
			
			$(document).ready(function(){
			
			let modifyFlag = false
			let memberProperty = '${memberVO.memberPropertyAsString}'
			let memberId = '${memberVO.memberId}';
			
			$('#btnModifyCancel').hide();
			$('#btnModifyMember').hide();
			
			const token = $("meta[name='_csrf']").attr("content");
        	const header = $("meta[name='_csrf_header']").attr("content");
        	const name = $("#userName").val();
			
			$.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
			
			$(document).on('click', '#btnModify', function(){ // 수정하기 버튼을 누르면
				 $('#checkModal').modal('show');
			});
			
			let countModify = 0;
			 $(document).on('click', '#btnPasswordCheck', function () { // 비밀번호 확인 버튼 누를시
	                let memberPassword = $('#memberPassword').val();
	                $.ajax({
	                    type: 'POST',
	                    url: '../member/check',
	                    contentType: 'application/json; charset=UTF-8',
	                    data: JSON.stringify({
	                        memberId: memberId,
	                        memberPassword: memberPassword
	                    }),
	                    success: function (result) {
	                        if (result == 1) {
	                        	modifyFlag = true;
	                        	$('#checkModal').modal('hide'); // 모달 닫기
	                        	$('#memberPassword').val(''); // 비밀번호 초기화
	                        	countModify++;
	            				if(countModify === 1){
	            					$('#emailDiv').append(
	            							'<input type="email" class="form-control" id="memberEmail" placeholder="이메일을 입력하세요.">' +
	            							'<span id="emailMsg"></span>' + 
	            							'<button id="btnSendCode">이메일 인증하기</button><span id="emailAuthMsg"></span>' +
	            							'<button type="button" id="btnEmailModify">이메일만 수정</button>'
	            							);
	            					$('#passwordDiv').append(
	            							'<input type="password" class="form-control" id="memberPassword" placeholder="비밀번호를 입력하세요."><span id="pwMsg"></span>' +
	            							'<input type="password" class="form-control" id="memberPasswordCheck" placeholder="한번더 비밀번호를 입력하세요."><span id="pwConfirmMsg"></span>' +
	            		    				'<button type="button" id="btnPasswordModify">비밀번호만 수정</button>'
	            							);
	            					if(memberProperty == 'null' ||  memberProperty.length === 0){
	            			    		$('#propertyDiv').html(
	            			    				'<label for="btnBuyProperty">현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<br>'
	            			    		        + '<button type="button" id="btnBuyProperty" class="btn btn-primary">상점</button></label>'
	            			    				);
	            			    	
	            			    	} else {
	            			    		$('#propertyDiv').html('삭제하실 항목을 선택해주세요.<br><ul></ul>');
	            			    		// 배열의 [ , ]을 공백으로 바꾼후 ', ' 을 기준으로 자른 배열
	            				    	let propertyArray = memberProperty.replace(/^\[|\]$/g, '').split(', ');
	            				    	// console.log(propertyArray);
	            					    for(let i = 0; i < propertyArray.length; i++){
	            					    	$('ul').append( 
	            				                    '<li style="list-style: none;">' + propertyArray[i] + '</li><input type="checkbox" id="checkValue" class="checkBox">'
	            					    	);
	            					    } // end for
	            					    $('#propertyDiv').append(
	            					    		'<button id="btnSelectAll">전체 선택</button><br><button id="btnDeleteSelect">선택 삭제</button>'
	            					    		);
	            			    	} // end if - else
	            				} else {
	            					
	            				}
	                        } else {
	                            $('#modalCheckMsg').html('잘못 입력하셨습니다.');
	                        }
	                    }
	                }); // end ajax
	            });
				
				$(document).on('click', '#btnSendCode', function(){
					authCodeSend($('#memberEmail').val());
				}); // end btnSendCode
				 
			 	let countdown;
				let timerInterval;
				let sendMailFlag = false; // 메일보내기 체커
				let emailFlag = false; // 메일 인증 체커
				let checkAuthCode; // 인증코드
				
				$(document).on('blur', '#memberEmail', function(){
					
	  				let memberEmail = $('#memberEmail').val();
	  				let emailRegExp = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/;
	  				// let emailRegExp = /(?:[a-zA-Z0-9._%+-]+@(?:naver\.com|daum\.net|kakao\.com|gmail\.com))/i;
	  				console.log(memberEmail);
	  				if(memberEmail === ""){
	  					$('#emailMsg').html("이메일도 필수에오. 필수!");
	  					$('#emailMsg').css("color", "red");
	  					emailFlag = false;
	  					return;
	  				}
	  				if(!emailRegExp.test(memberEmail)){
	  					$('#emailMsg').html("유효한 형식의 이메일이 아니에오");
	  					$('#emailMsg').css("color", "red");
	  					emailFlag = false;
	  				} else {
			  			checkMail(memberEmail);
		  		  	}
				}) // end email 체크
				
				function checkMail(memberEmail){
					$.ajax({
			  			type : "GET",
			  			url : "../util/checkEmail/",
			  			data: { memberEmail : memberEmail },
			  			success : function(result){
			  				if(result == 1){
			  					$('#emailMsg').html("누군가 벌써 사용중인 이메일입니다.");
			  					$('#emailMsg').css("color", "red");
			  					emailFlag = false;
			  				} else {
			  					$('#emailMsg').html("사용가능한 이메일입니당.");
			  					$('#emailMsg').css("color", "green");
			  					sendMailFlag = true;
			  				}
			  			}
			  		});	// end ajax
				}
				
				function authCodeSend(memberEmail) {
					if(sendMailFlag){
					    $.ajax({
					        type: "GET", // GET 요청 타입
					        url: "../util/authCodeSend/", // 요청을 보낼 URL
					        data: { memberEmail: memberEmail }, // 요청에 포함할 데이터
					        success: function(response) { // 요청이 성공적으로 완료되었을 때 실행할 함수
					            if (response.result === 1) { // 서버로부터 성공 응답을 받았을 때
					                alert("작성하신 이메일로 확인 코드가 발송되었습니다.");
					                $('#emailAuthMsg').html(
					                    "<input id='authCode' type='number' placeholder='코드를 입력해 주세요.'>" +
					                    "<button id='btnCodeCheck'>인증확인</button><br>" +
					                    "<span id='checkAuthMsg'></span><br>" +
					                    '<span id="timer"></span>'
					                );
					                sendMailFlag = false;
					                checkAuthCode = response.authCode; // 인증코드
					                console.log(checkAuthCode);
							   		let count = 30; // 타이머 지속 시간 (초)
							   		countdown = function(){
						               if (count > 0) {
						                   count--; // 지속 시간 감소
						                   let min = Math.floor(count / 60); // 남은 분 계산
						                   let sec = count % 60; // 남은 초 계산
						                   sec = sec < 10 ? '0' + sec : sec;
						                 $('#timer').text(
						               		  '0' + min + ':' + sec
						                 );
						               } else {
						                   checkAuthCode = null; // 인증 코드를 무효화
						                   clearInterval(timerInterval); // 타이머 정지
						                   alert('코드 인증시간이 만료되었습니다.'); // 사용자에게 알림
						                   sendMailFlag = true;
						               }
						           } // end countdown
							        // 매 초마다 countdown 함수 호출        	
							        timerInterval = setInterval(countdown, 1000);
					            } else {
					                alert("잠시후 다시 눌러주세요."); // 서버로부터 실패 응답을 받았을 때
					            }
					        }
					    }); // end ajax
					} else{
						alert('인증을 이미 하셨거나, 사용이 불가능한 이메일 또는 이메일이 보내졌습니다. 인증시간 만료후 다시 눌러주세요.');
					}
				} // end authCodeSend()
			 
			$(document).on('click', '#btnCodeCheck', function(){
				let checkCode = $('#authCode').val();
				if(checkAuthCode == checkCode){
					$('#checkAuthMsg').html('인증에 성공하셨습니다');
					$('#checkAuthMsg').css('color', 'green');
					emailFlag = true;
					clearInterval(timerInterval);
				} else{
					$('#checkAuthMsg').html('인증에 실패했습니다.');
					$('#checkAuthMsg').css('color', 'red');
					emailFlag = false;
				}
			});
				
			$(document).on('click', '#btnEmailModify', function(){
				let memberEmail = $('#memberEmail').val();
				let memberId = '${sessionScope.memberId}';
				if(emailFlag){
					$.ajax({
		                type: 'POST',
		                url: '../member/modifyEmail',
		                contentType: 'application/json; charset=UTF-8',
		                data: JSON.stringify({
		                	memberId : memberId,
		                    memberEmail : memberEmail
		                }),
		                success: function(result) {
		                    if (result === 1) {
		                        alert('이메일이 성공적으로 변경되었습니다.');
		                    } else {
		                        alert('죄송합니다 현재 서버에 문제가 생긴것 같아요.');
		                    }
		                }
			    	}); // end ajax
				} else {
					alert('메일 인증을 확인해 주세요.');
				}
			});
			
			let pwFlag = false;
			let pwFlagConfirm = false;
			$(document).on('blur', '#memberPassword', function(){
  				let memberPw = $('#memberPassword').val();
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
			}); // end 비밀번호 유효성 검사
	  		
			$(document).on('blur', '#memberPasswordCheck', function(){
				
  				let pwConfirm = $('#memberPasswordCheck').val();
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
			}) // end 비밀번호 확인 유효성 검사
			
			 $(document).on('click', '#btnPasswordModify', function(){
				 let memberPassword = $('#memberPassword').val();
					if(pwFlag && pwConfirmFlag){
						$.ajax({
			                type: 'POST',
			                url: '../member/modifyPw',
			                contentType: 'application/json; charset=UTF-8',
			                data: JSON.stringify({
			                	memberId : memberId,
			                    memberPassword: memberPassword
			                }),
			                success: function(result) {
			                    if (result === 1) {
			                        alert('비밀번호가 성공적으로 변경되었습니다.');
			                    } else {
			                        alert('죄송합니다 현재 서버에 문제가 생긴것 같아요.');
			                    }
			                }
				    	}); // end ajax
					}
				}); // end passwordModify

		    $(document).on('click', '#btnBuyProperty', function(){
			    window.location.href = 'http://localhost:8080/ex01/shop/imoji';
		    });
		    
		    $('#btnBackward').click(function(event) {
				event.preventDefault();
				window.location.href = '/ex01/';
			});
			
	    	// let propertyArray = memberProperty.replace(/^\[|\]$/g, '').split(', ');
	    	// console.log(propertyArray);
		    
	    	
	    	let deleteList = []; // 삭제할 상품이 추가될 배열 선언
	    	$(document).on('click', '.checkBox', function(event){
	    		let checked = $(this).is(':checked');
	    		let selectedItem = $(this).prev().text(); // 이전 요소의 텍스트
	    		let item = parseInt(selectedItem, 0); // 혹시라도 값이 잘못되어 파싱을 못하는 경우 0으로
	    		console.log(this); // 선택된 체크박스
	    		console.log('item : ' + item);
	    		if(checked == true){ // 체크 된 경우
	    			deleteList.push(item);
	    			console.log(deleteList);
	    		} else{
	    			let index = deleteList.indexOf(item); // 배열에서 item과 일치하는 곳의 인덱스값
	    	        if (index > -1) { // item이 존재할시
	    	            deleteList.splice(index, 1); // 배열에서 해당 인덱스를 제거(위치로부터 1개만 제거);
	    	            console.log(deleteList);
	    	        }
	    		}
		    })
		    
		    $(document).on('click', '#btnDeleteSelect', function(event){
		    	event.preventDefault();
		    	let checked = $('ul input[type="checkbox"]:checked');
		    	let isConfirmed = confirm('정말 삭제하시겠습니까?');
		    	if(isConfirmed){
			    	$.ajax({
			    		type : 'POST',
			    		url : '../member/deleteProperty',
			    		contentType: 'application/json; charset=UTF-8',
			    		data : JSON.stringify({
			    			memberId : memberId,
			    			memberProperty : deleteList
			    		}),
			    		success : function(result){
			    			if(result == 1){
			    				alert('삭제되었습니다.');
			    				checked.each(function(){
			    					let delProd = $(this).prev();
			    					delProd.remove();
			    					$(this).remove();
			    				});
			    			} else{
			    				alert('이모지 삭제에 문제가 생겻습니다.');
			    			}
			    		}
			    	}); // end ajax
		    	}
		    }); // end btnDeleteProperty()
		    
		    
		    let deleteFlag = false;
		    $(document).on('click', '#btnDelete', function(){
		    	let isConfirmed = confirm('탈퇴하시겠습니까?');
		    	if(isConfirmed){
		    		$('#deleteModal').modal('show');
		    		$('#deleteMsg').html(
		    				'정말 탈퇴하시려면 <strong>회원님의 아이디<strong>를 입력한 후 확인 버튼을 눌러주세요.'
		    				+ '<br>'
		    				+ '<input type="text" id="delMemberId" placeholder="회원님의 아이디">'
		    				+ '<br>'
		    				+ '<span id="delCheckMsg"></span>'
		    				+ '<br>'
		    				+ '<button id="btnDeleteCheck">확인</button>'
		    		);
		    	}
		    });  
		    
		    $(document).on('blur', '#delMemberId', function(){
		    	let delMemberId = $('#delMemberId').val();
		    	if(delMemberId != '${memberVO.memberId}'){
		    		$('#delCheckMsg').html('정확한 회원님의아이디를 입력해주세요.');
		    		$('#delCheckMsg').css('color', 'red');
		    		deleteFlag = false;
		    	} else {
		    		$('#delCheckMsg').html('');
		    		deleteFlag = true;
		    	}
		    });
		    
		    $(document).on('click', '#btnDeleteCheck', function(event){
		    	event.preventDefault();
			    	if(deleteFlag){
			    		$.ajax({
			    			type : 'POST',
			    			url : 'delete',
			    			contentType: 'application/json; charset=UTF-8',
			    			data : JSON.stringify({
			    				memberId : memberId
			    			}),
			    			success : function(result){
			    				if(result == 1){
			    					alert('탈퇴가 성공적으로 이루어졌슨니다.');
			    					window.location.href='checkout';
			    				} else{
			    					alert('죄송합니다. 잠시후 새로고침후 다시 해주세요.');
			    				}
			    			}
			    		});
			    	} else {
			    		$('#delCheckMsg').html('아이디를 정확히 입력해주세요.');
			    	}
		    }); // end 탈퇴 확인 버튼
		    
		    // 필요하면 회원 수정 완료후 다시 원래 화면으로 만들도록 코드 추가할것
		    
		}); // end document ready
	</script>
</body>
</html>

