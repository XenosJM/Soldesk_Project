<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<body>
	<h2><c:out value="${memberVO.memberId}님의 정보입니다."></c:out></h2>
	<p>현재 이메일 : ${memberVO.memberEmail }</p><br>
					
	<c:choose>
    <c:when test="${empty memberVO.memberProperty}">
        <label for="buyProperty">
            현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<!-- //TODO 변경할 예정 -->
            <input type="submit" id="buyProperty" onclick="location.href='http://localhost:8080/ex01/shop/imoji'" value="상점">
        </label>
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
	<p>회원가입 날짜 : ${memberVO.memberRegistDate }</p><br>
	
	
	<button id="modify" class="btn btn-primary">회원정보 수정</button>
	<button id="delete" class="btn btn-primary">회원탈퇴</button>
	<button id="btnBackward" class="btn btn-primary">뒤로가기</button>
	
	<div class="modal" id="modifyModal">
    	<div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title">회원정보 수정</h4>
	                <button type="button" class="close" data-dismiss="modal">&times;</button>
	            </div>
	            <div class="modal-body">
	                <form id="modifyForm">
	                    <div class="form-group" id="divPw">
	                    </div>
	                    <div class="form-group" id="divProperty">
	                    	<!-- 스팬에 if로 값을 뿌려줄것. -->
	                    	<span id="modalProperty">
	                    	<button type="button" class="btn btn-primary" id="btnPropertyModify">이모지 수정하기</button>
	                    		
	                    		<span id="notEmptyProperty"></span>
	                    	</span>
	                        <c:choose>
							    
							    <c:when test="${not empty memberVO.memberProperty}">
							        <p>현재 보유중인 상품 목록:</p>
							        <ul id="propertyList">
							        </ul>
							        <br>
							    </c:when>
							</c:choose>
	                    </div>
	                    <div class="form-group">
	                    	<span id="modalEmail"></span>
	                        <button type="button" class="btn btn-primary" id="btnEmail">이메일</button>
	                        <input type="email" class="form-control" id="memberEmail" placeholder="이메일을 입력하세요.">
	                    </div>
	                    <button type="button" class="btn btn-primary" id="btnFormSubmit">수정하기</button>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
	
 	<script>
$(document).ready(function(){
	let 
	let memberId = '${memberVO.memberId}';
	
	$(document).on('click', '#modify', function(){
        $('#modifyModal').modal('show');
        $('#divPw').html('<span id="modalPw"></span>'
				+ '<button type="button" class="btn btn-primary" id="btnPw">비밀번호 수정하기</button>');
    }); // end modify
    
    $(document).on('click', '#btnPw', function(event){
    	event.preventDefault();
    	
    	if($('#memberPw').length > 0){
    		console.log('나 여기있다.');
    	} else {
    		$('#modalPw').html(
    				'<input type="password" class="form-control" id="memberPw" placeholder="비밀번호를 입력하세요.">'
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
   
    let memberProperties = ${memberVO.memberPropertyAsString};
    // let memberProperties = '${memberVO.memberProperty}' != null ? 'memberVO.memberProperty' : '[]';
    $(document).on('click', '#btnPropertyModify', function(event){
    	event.preventDefault();
    	
    	if(memberProperties == null){
    		$('#divProperty').html(
    				'<span id="emptyProperty">' 
    				+ '<label for="btnBuyProperty">현재 보유중인 상품이 없습니다. 구매하러 가시겠습니까?<br>'
    		        + '<button type="button" id="btnBuyProperty" class="btn btn-primary">상점</button></label></span>'
    				);
    		$('#btnBuyProperty').show();
    		
    	} else if (memberProperties != null) {
		    //let propertiesArray = memberProperties.split(",");
    		
    	}
    			
    	$('#btnPropertyModify').hide();
    });  // end 이모지 수정 btnPropertyModify
    
    $(document).on('click', '#btnPropertyModifyCancel', function(event){
		event.preventDefault();
		$('#modalPw').html('');
		$('#btnPropertyModify').show();
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
    	let isConfirmed = confirm('정말 삭제하시겠습니까?');
    	// 몇번째 항목이 클릭되었는지 알 수 있도록 코드를 짜야함.
    	if(isConfirmed){
    	let propertyIndex = $(this).closest('li').index();
    		
	    	$.ajax({
	    		type : 'POST',
	    		url : '../util/deleteProperty',
	    		data : {
	    			memberId : memberId,
	    			propertyIndex : propertyIndex,
	    		},
	    		success : function(result){
	    			if(result == 1){
	    				alert('삭제되었습니다.');
	    			} else{
	    				alert('이모지 삭제에 문제가 생겻습니다.');
	    			}
	    		}
	    	}); // end ajax
    	}
    }); // end btnDeleteProperty()
    
    $('#btnFormSubmit').click(function(){
        let memberPassword = $('#memberPassword').val();
        let memberEmail = $('#memberEmail').val();
        
        if (memberPassword && memberEmail) {
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
        } else {
            alert('모든 필드를 입력해주세요.');
        }
    }); // end btnFormSubmit
    
    // 인덱스 길이 즉, 회원 자산(이모지 등)은 foreach구문으로 생성된 요소 갯수만큼이다.
    // 그렇다는건 인덱스의 길이를 알아냈다는 소리이고, 이를 이용해 부모요소의 몇번째 자식요소 즉,
    // 선택된 요소가 무엇인지를 통해 선택된 인덱스의 번호를 알수가있다.
    // 이를 이용해 인덱스를 이용해 삭제 및 업데이트가 가능하다.
    
    // 부모요소(span div 등)의 자식요소(li태그)의 갯수가 몇개인지 알수있다.
    // 몇번째 자식 요소가 선택(클릭)된지를 알수가 있다.
    
}); // end document ready
</script>
</body>
</html>

