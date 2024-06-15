<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--시큐리티 태그를 사용하귀 위해 입력. --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<sec:csrfMetaTags/>
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
    	
    	const token = $("meta[name='_csrf']").attr("content");
       	const header = $("meta[name='_csrf_header']").attr("content");
       	const name = $("#userName").val();
    	
    	$.ajaxSetup({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
    	
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
    			success : function(data){
    				console.log(data);
    				$.each(data, function(index, item){
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
    			success : function(data){
    				$.each(data, function(index, item){
    					let recState = function(item){
    						if(item.receiveState === null){
    							return '대기중'
    						} else if(item.receiveState === 'accept'){
    							return '수락됨'
    						} else if(item.receiveState === 'reject'){
    							return '거절됨'
    						}
    					};
    					if(recState(item) === '대기중'){
	    					$('#receiveRequest').append(
		    					'<li>' + item.requesterId +  
		    					'<input id="recState" disabled type="text" value=' + recState(item) + '>' +
		    					'<button id="btnAccept">수락</button>' +
		    					'<input type="hidden" id="receiveId" value='+ item.receiveId +'>' +
			    				'<button id="btnReject">거절</button>' +
			    				'</li>'
		    				);
    					} else {
    						$('#receiveRequest').append(
    		    					'<li>' + item.requesterId +  
    		    					'<input id="recState" disabled type="text" value=' + recState(item) + '>' +
    		    					'<input type="hidden" id="receiveId" value='+ item.receiveId +'>' +
    			    				'</li>'
    		    			);
    					}
    				}); // end each
    			} // end success
    		}); // end ajax
    	}); // end btnReceiveRequestList
    	
    	$(document).on('click', '#btnRequestCancel', async function(){
    	    let requestId = $(this).prev().val();
    	    let receiveId = $(this).prev().val();
    	    let requestState = 'cancel';
			let receiveState = 'cancel';
    	    //console.log('Request ID:', requestId);
    	    //console.log('Receive ID:', receiveId);
    	    //console.log('Request State:', requestState);

    	    try {
    	        const [updateResponse, insertResponse] = await Promise.all([
    	            $.ajax({
    	                type: 'post',
    	                url: '../friend/requestState',
    	                contentType: 'application/json; charset=UTF-8',
    	                data: JSON.stringify({
    	                	requestId : requestId,
    	                	requestState: requestState
    	                })  // 데이터 전송을 위해 JSON.stringify() 사용
    	            }),
    	            $.ajax({
    	                type: 'post',
    	                url: '../friend/receiveState',
    	                contentType: 'application/json; charset=UTF-8',
    	                data: JSON.stringify({ 
    	                	receiveId : receiveId,
    	                	receiveState : receiveState
    	                })  // 데이터 전송을 위해 JSON.stringify() 사용
    	            })
    	        ]);

    	        //console.log('Update Response:', updateResponse);
    	        //console.log('Insert Response:', insertResponse);
    	        alert('친구요청이 취소 되었습니다.');
    	    } catch (error) {
    	        console.error('Error:', error);
    	        alert('친구 요청 처리 중 오류가 발생했습니다.');
    	    }
    	});
    	
    	$(document).on('click', '#btnAccept', function(){
    		// 업데이트이후 친구목록에 2번 추가진행  
    	})
    	
    	$(document).on('click', '#btnReject', function(){
    		// 보낸요청 받은요청 업데이트 작업
    	})
    	
    	
    	// 전송된 채팅이 존재할시 친구 이름 주황색으로 변경되게 할것
    	// 웹소켓으로 변경시 탭이활성화 되면 수락 또는 거절 목록을 5초뒤 삭제하도록 명령문 실행되게 짤것
    	// 웹소켓 연결시켜놓고 새로고침하면 다시 재연결 시키도록 코드 짜기
    	
    	
    	
    });
    </script>
</body>
</html>





