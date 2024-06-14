<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fixed Button with Scroll</title>
    <style>
    .button-container {
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 1000;
    }

    .button {
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .tab.active {
        background-color: #ccc;
    }

    .content {
        display: none;
        padding: 10px;
        border: 1px solid #ccc;
        border-top: none;
    }

    .content.active {
        display: block;
        margin-top: 60px; /* 버튼 영역의 높이 + 여백 */
    }

    .friend-list {
        list-style-type: none;
        padding: 0;
    }

    .friend-list li {
        padding: 8px;
        border-bottom: 1px solid #ddd;
    }

    .online {
        color: green;
    }

    .offline {
        color: black;	
    }
</style>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>

	<div class="button-container">
	    <button id="btnShowFriendList" class="tab">친구 목록</button>
	    <button id="btnSendRequestList" class="tab">보낸 요청</button>
	    <button id="btnReceiveRequestList" class="tab">받은 요청</button>
	</div>
	
	<div id="tabList">
	    <div id="onlineOfflineTab" class="content">
	        <h5>온라인 친구</h5>
	        <ul class="friend-list" id="onlineFriend">
	        </ul>
	        <h5>오프라인 친구</h5>
	        <ul class="friend-list" id="offlineFriend">
	        </ul>
	    </div>
	    <div id="sendRequestTab" class="content">
	        <h5>친구 요청 보낸 목록</h5>
	        <ul class="friend-list" id="sendRequest">
	        </ul>
	    </div>
	    <div id="receivedRequestTab" class="content">
	        <h5>친구 요청 받은 목록</h5>
	        <ul class="friend-list" id="receiveRequest">
	        </ul>
	    </div>
	</div>
	
    <script type="text/javascript">
    $(document).ready(function(){
    	
    // 대기중인 요청만 리스트로 가져오고 거절 또는 수락 된 요청은 안가져옴, 수락 거절 된 요청들은 정해진시간에 스케쥴러를 이용해 제거하도록 할 예정
    // <li class="online">친구1</li>  <li class="offline">친구3</li>
    	
    	//let friendList =${friendList}; // 모델로 받아온 List배열
    	let onlineFriend = []; // 친구의 상태가 온라인인지 오프라인 인지에 따라 나눌 배열
    	let offlineFriend = [];
   		let memberId = '${sessionScope.memberId}';
    	
    	let onlineList = function(onlineFriend){
		    $.each(onlineFriend, function(index, friend){
		        $('#onlineFriend').append('<li class="online">' + friend.friendMemberId + '</li>');
		    });
    	}
		
    	let offlineList = function(offlineFriend){
		    $.each(offlineFriend, function(index, friend){
		        $('#offlineFriend').append('<li class="offline">' + friend.friendMemberId + '</li>');
		    });
    	}
    	
	    $(document).on('click', '.tab', function() {
            // 모든 탭에서 active 클래스를 제거하고
            $('.tab').removeClass('active');
            // 클릭된 탭에만 active 클래스를 추가합니다.
            $(this).addClass('active');
        });
	    
	    function cleanTab(){
	    	// $('#receiveRequest').html('');
	    	// $('#sendRequest').html('');
    		// $('#onlineFriend').html('');
    		// $('#offlineFriend').html('');
    		$('.friend-list').html('');
	    }
	    
    	$(document).on('click', '#btnShowFriendList', function(){
    		$('.content').removeClass('active');
    		$('#onlineOfflineTab').addClass('active');
    		cleanTab();
    		$.ajax({
    			type : 'GET',
    			url : '../friend/getFriend/' + memberId, 
    			success : function(data){
    				if(data != null){
    					data.forEach(function(item) {
    						// 받아온 data의 값중 friendState가 online인 경우
    					    if (item.friendState === 'online') {
    					    	// onlineFriend 배열에 item.friendMemberId값을 가진 값이 하나이상 있는지 여부 
    					    	let exist = onlineFriend.some(function(friend){
    					    		return friend.friendMemberId === item.friendMemberId
    					    	});
    					    	// 없다면 추가
    					        if (!exist) {
    					            onlineFriend.push(item);
    					        }
    					    } else if (item.friendState === 'offline') {
    					    	let exist = offlineFriend.some(function(friend){
    					    		return friend.friendMemberId === item.friendMemberId
    					    	});
    					        if (!exist) {
    					            offlineFriend.push(item);
    					        }
    					    }
    					});
	    		    	    onlineList(onlineFriend);
	    		    	    offlineList(offlineFriend);
    				} else{
    					
    				}
    			}
    		});
		});
    	
    	$(document).on('click', '#btnSendRequestList', function(){
    		$('.content').removeClass('active');
    		$('#sendRequestTab').addClass('active');
    		cleanTab();
    		$.ajax({
    			type : 'GET',
    			url : '../friend/getRequest/' + memberId, 
    			success : function(requestList){
    				$.each(requestList, function(index, item){
    					let reqState = function(item){
    						if(item.requestState === null){
    							return '대기중'
    						} else if(item.requestState === 'accept'){
    							return '수락됨'
    						} else if(item.requestState === 'reject'){
    							return '거절됨'
    						}
    					};
    		    	   	$('#sendRequest').append(
    		    				'<li>' + item.receiverId + 
    		    				'<input disabled type="text" value=' + reqState(item) +
    		    				' style="border: none; background: transparent;">' +
    		    				'<input type="hidden" id="requestId" value='+ item.requestId +'>' +
    		    				'<button id="btnRequestCancel">취소</button></li>'
   		    		   	);    						
   					}); // end each
				}
    		}); // end ajax
    	}); // end btnSendRequestList
    	
    	$(document).on('click', '#btnReceiveRequestList', function(){
    		$('.content').removeClass('active');
    		$('#receivedRequestTab').addClass('active');
    		cleanTab();
    		$.ajax({
    			type : 'GET',
    			url : '../friend/getReceive/' + memberId, 
    			success : function(receiveList){
    				$.each(receiveList, function(index, item){
    					$('#receiveRequest').append(
	    					'<li>' + item.requesterId +  
	    					'<button id="btnAccept">수락</button>' +
	    					'<input type="hidden" id="receiveId" value='+ item.receiveId +'>' +
		    				'<button id="btnReject">거절</button>' +
		    				'</li>'
	    				);
    				}); // end each
    			} // end success
    		}); // end ajax
    	}); // end btnReceiveRequestList
    	
    	$(document).on('click', '#btnRequestCancel', function(){
    		// 삭제 두번
    		
    	})
    	
    	$(document).on('click', '#btnAccept', function(){
    		// 삭제후 친구목록에 2번 추가 
    	})
    	
    	$(document).on('click', '#btnReject', function(){
    		// 보내요청 받은요청 삭제 작업
    	})
    	
    	
    	// 전송된 채팅이 존재할시 친구 이름 주황색으로 변경되게 할것
    	
    	
    	
    	
    });
    </script>
</body>
</html>





