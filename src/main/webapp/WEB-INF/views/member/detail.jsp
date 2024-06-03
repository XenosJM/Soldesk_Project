<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
		<h2><c:out value="${memberVO.memberId}"></c:out>님의 정보입니다.</h2>
		<br>
	<div id="emailDiv">
		<c:out value="현재 이메일 : ${memberVO.memberEmail}"></c:out>
	</div>
	<br>
	<div id="passwordDiv"></div>
	
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
	
	<c:out value="회원가입 날짜 : ${memberVO.memberRegistDate }"></c:out>
	<br>
	
	<button id="btnModify" class="btn btn-primary">회원정보 수정</button>
	<button id="btnModifyCancel" class="btn btn-primary">수정 취소</button>
	<button id="btnModifyMember" class="btn btn-primary">전체 수정하기</button>
	<button id="btnBackward" class="btn btn-primary">뒤로가기</button>
	
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
	        	
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#btnModifyCancel').hide();
			$('#btnModifyMember').hide();
			
			let memberId = '${memberVO.memberId}';
			let memberProperty = '${memberVO.memberPropertyAsString}'
			
			let modifyFlag = false
			
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
	            							'<button id="btnSendCode">이메일 인증하기</button><br><span id="emailAuthMsg"></span>' +
	            							'<button type="button" id="btnEmailModify">이메일만 수정</button>'
	            							);
	            					$('#passwordDiv').append(
	            							'<input type="password" class="form-control" id="password" placeholder="비밀번호를 입력하세요."><span id="passwordMsg"></span>' +
	            							'<input type="password" class="form-control" id="passwordCheck" placeholder="한번더 비밀번호를 입력하세요."><span id="checkMsg"></span>' +
	            		    				'<button type="button" id="btnPassword">비밀번호만 수정</button>'
	            							);
	            					if(memberProperty == 'null' ||  memberProperty.length === 0){
	            			    		$('#propertyDiv').html(
	            			    				'<label for="btnBuyProperty">현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<br>'
	            			    		        + '<button type="button" id="btnBuyProperty" class="btn btn-primary">상점</button></label>'
	            			    				);
	            			    		
	            			    	} else {
	            			    		$('#propertyDiv').html('삭제할 이모티콘을 선택해 주세요.');
	            			    		// 배열의 [ , ]을 공백으로 바꾼후 ', ' 을 기준으로 자른 배열
	            				    	let propertyArray = memberProperty.replace(/^\[|\]$/g, '').split(', ');
	            				    	// console.log(propertyArray);
	            					    for(let i = 0; i < propertyArray.length; i++){
	            					    	$('#propertyDiv').append( // ul의 자식중 checkValue가 true인 애들의 가장 가까운 li의 text 또는 값을 가져다가 배열로 만들고 배열에 추가할 예정
	            				                    '<div class="checkbox-container"><ul><li style="list-style: none;">' + propertyArray[i] + '</li><input type="checkbox" id="checkValue" class="checkBox"></ul></div>'
	            					    	);
	            					    } // end for
	            					    $('#propertyDiv').append(
	            					    		'<button id="selectAll">전체 선택</button><br><button id="deleteSelect">선택 삭제</button>'
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
			 
			$(document).on('click', '#modifyImoji', function(event){
				console.log(this);
		        $('#modifyModal').modal('show');
		        $('#modifyModal').html(
		        		'<div class="modal-dialog">'
		    	        + '<div class="modal-content">'
		    	        + '<div class="modal-header">'
		    	        + '<h4 class="modal-title">이모티콘 제거</h4>'
		    	        + '<button type="button" id="closeModal" class="close" data-dismiss="modal">&times;</button>'
		    	        + '</div>'
		    	        + '<div class="modal-body">'
		    	        + '<form id="modifyForm">'
		    	        + '<div class="form-group" id="divProperty"></div>'
		    	        + '</form>'
		    	        + '</div>'
		    	        + '</div>'
		    	    	+ '</div>'	
		        );
		        $('#divPw').html('<span id="modalPw"></span>'
						+ '<button type="button" class="btn btn-primary" id="btnPw">비밀번호 수정하기</button>'
		        ); // end divPw.html
		        $('#divProperty').html(
		        		'<div id="emptyProperty">'
		        		+ '<div id="notEmptyProperty"><ul id="propertyList"></ul></div>'
		        		+ '<button type="button" class="btn btn-primary" id="btnPropertyModify"> 이모지 수정하기</button>'
		        		+ '<button type="button" class="btn btn-primary" id="btnPropertyModifyCancel"> 이모지 수정 취소</button>'
		        ); // end divProperty.html
		        $('#divEmail').html(
		        		'<div id="emailModify"></div>'
		        		+ '<button type="button" class="btn btn-primary" id="btnEmailModify">이메일 수정</button>'
		        ); // end divEmail
		        $('#btnPropertyModifyCancel').hide();	
		    }); // end modify
		    
		    $(document).on('click', '#btnPw', function(event){
		    	event.preventDefault();
		    	
		    	if($('#memberPw').length > 0){
		    		// 추가 생성방지
		    	} else {
		    		$('#modalPw').html(
		    				'<input type="password" class="form-control" id="memberPassword" placeholder="비밀번호를 입력하세요.">'
		    				+ '<button type="button" class="close" id="btnPwCancel">&times;</button>'
		    		);
		    		$('#btnPw').hide();
		    	}
		    }); // end 비밀번호 변경 btnPw
		    
			$(document).on('click', '#btnPwCancel', function(event){
				event.preventDefault();
				$('#modalPw').html('');
				$('#btnPw').show();
		    }); // end 요소 취소
		   
		    $(document).on('click', '#btnPropertyModify', function(event){
			    let memberProperties = '${memberVO.memberPropertyAsString}';
		    	event.preventDefault();
		    	$('#btnPropertyModify').hide();
		    	$('#btnPropertyModifyCancel').show();
		    	if(memberProperties == 'null' ||  memberProperties.length === 0){
		    		$('#emptyProperty').html(
		    				'<label for="btnBuyProperty">현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<br>'
		    		        + '<button type="button" id="btnBuyProperty" class="btn btn-primary">상점</button></label>'
		    				);
		    		
		    	} else {
			    	let propertiesArray = memberProperties.replace(/^\[|\]$/g, '').split(', ');
			    	console.log(propertiesArray);
				    for(let i = 0; i < propertiesArray.length; i++){
				    	$('#propertyList').append(
				                '<li>' + propertiesArray[i] + '<div id="btnZone"><button type="button" id="btnDeleteProperty">&times;</button></div></li>'
				            );
				    } // end for
		    	} // end if - else
		    });  // end 이모지 수정 btnPropertyModify
		    
		    $(document).on('click', '#btnEmailModify', function(event){
		    	$('#emailModify').html(
		    			'<input type="email" class="form-control" id="memberEmail" placeholder="이메일을 입력하세요.">'
		    			+ '<button type="button" class="btn btn-primary" id="btnEmailModifyCancel">&times;</button>'
		    	);
		    	$('#btnEmailModify').hide();
		    }); // end btnEmailModofy
		    
		    $(document).on('click', '#btnEmailModifyCancel', function(event){
		    	event.preventDefault();
		    	$('#emailModify').html('');
		    	$('#btnEmailModify').show();
		    	
		    });
		    
		    $(document).on('click', '#btnPropertyModifyCancel', function(event){
				event.preventDefault();
				$('#propertyList').html('');
				$('#btnPropertyModify').show();
				$('#btnPropertyModifyCancel').hide();
		    }); // end 요소 취소
		    
		    $(document).on('click', '#btnBuyProperty', function(){
			    window.location.href = 'http://localhost:8080/ex01/shop/imoji';
		    });
		    
		    $('#btnBackward').click(function(event) {
				event.preventDefault();
				window.location.href = '/ex01/';
			});
			
		    $(document).on('click', '#btnDeleteProperty', function(event){
		    	event.preventDefault();
		    	
		    	let propertyIndex = $(this).closest('li').index(); // 클릭된 버튼의 가장 가까운 li태그의 인덱스
		        // console.log(this);
		   		// console.log('propertyIndex : ' + propertyIndex);
		    	let memberId = '${memberVO.memberId}';
		    	let isConfirmed = confirm(', 이 이모지를 정말 삭제하시겠습니까?');
		    	// 몇번째 항목이 클릭되었는지 알 수 있도록 코드를 짜야함.
		    	
		    	if(isConfirmed){
		    		$(this).closest('li').remove();
			    	$.ajax({
			    		type : 'POST',
			    		url : 'deleteProperty',
			    		data : {
			    			memberId : memberId,
			    			propertyIndex : propertyIndex
			    		},
			    		success : function(result){
			    			if(result == 1){
			    				$(this).closest('li').remove();
			    				alert('삭제되었습니다.');
			    			} else{
			    				alert('이모지 삭제에 문제가 생겻습니다.');
			    			}
			    		}
			    	}); // end ajax
		    	}
		    }); // end btnDeleteProperty()
		    
		    $(document).on('click', '#btnFormSubmit', function(){
		        let memberPassword = $('#memberPassword').val();
		        console.log(memberPassword);
		        let memberEmail = $('#memberEmail').val();
		        console.log(memberEmail);
		        
		        if (memberPassword === '' || memberEmail === '') {
		            alert('모든 필드를 입력해주세요.');
		        } else {
		            $.ajax({
		                type: 'POST',
		                url: '../util/modifyPw/',
		                contentType: 'application/json; charset=UTF-8',
		                data: JSON.stringify({
		                    memberPassword: memberPassword,
		                    memberEmail: memberEmail
		                }),
		                success: function(result) {
		                    if (result === 1) {
		                        alert('비밀번호가 성공적으로 변경되었습니다. 뒤로 돌아간뒤 변경된 비밀번호로 로그인하여 주세요.');
		                        $('#modifyModal').modal('hide');
		                    } else {
		                        alert('죄송합니다 현재 서버에 문제가 생긴것 같아요.');
		                    }
		                }
		            }); // end ajax
		        }
		    }); // end btnFormSubmit
		    
		    $(document).on('click', '#delete', function(){
		    	let isConfirmed = confirm('탈퇴하시겠습니까?');
		    	if(isConfirmed){
		    		$('#deleteMsg').html(
		    				'정말 탈퇴하시려면 <strong>회원님의 아이디<strong>를 입력한 후 확인 버튼을 눌러주세요.'
		    				+ '<input type="text" id="delMemberId" placeholder="회원님의 아이디">'
		    				+ '<span id=delCheckMsg></span>'
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
		    	if(!deleteFlag){
		    		
		    	} else{
		    		
			    	let isConfirmed = confirm('계속 진행하시겠습니까?');
			    	if(isConfirmed && deleteFlag){
			    	let memberId = '${memberVO.memberId}';
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
			    		alert('잘 생각하셨어요!');
			    	}
		    	}
		    });
		    
		}); // end document ready
	</script>
</body>
</html>

