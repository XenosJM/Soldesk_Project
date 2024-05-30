<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>친구 목록</title>
    <style>

        .tab.active {
            background-color: #ccc;
        }

        .tab-content {
            display: none;
            padding: 10px;
            border: 1px solid #ccc;
            border-top: none;
        }

        .tab-content.active {
            display: block;
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
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <div class="tabs">
    	<button id="btnShowFriendList" class="tab active">친구 목록</button>
        <button id="btnSendRequestList" class="tab">보낸 요청</button>
        <button id="btnReceiveRequestList" class="tab">받은 요청</button>
    </div>
	<div id="tabList" class="tab">
	    <div id="onlineOfflineTab" class="tab-content active">
	        <h3>온라인 친구</h3>
	        <ul class="friend-list" id="onlineFriend">
	        </ul>
	        <h3>오프라인 친구</h3>
	        <ul class="friend-list" id="offlineFriend">
	        </ul>
	    </div>
	</div>

    <div id="sentRequestTab" class="tab-content">
        <h3>친구 요청 보낸 목록</h3>
        <ul class="friend-list" id="sendRequest">
        </ul>
    </div>

    <div id="receivedRequestTab" class="tab-content">
        <h3>친구 요청 받은 목록</h3>
        <ul class="friend-list" id="receiveRequest">
        </ul>
    </div>
	
    <script type="text/javascript">
    $(document).ready(function(){
    	
    // 대기중인 요청만 리스트로 가져오고 거절 또는 수락 된 요청은 안가져옴, 수락 거절 된 요청들은 정해진시간에 스케쥴러를 이용해 제거하도록 할 예정
    // <li class="online">친구1</li>  <li class="offline">친구3</li>
    	
    	let friendList =${friendList}; // 모델로 받아온 List배열
    	let onlineFriend = []; // 친구의 상태가 온라인인지 오프라인 인지에 따라 나눌 배열
    	let offlineFriend = [];
		
    	// 모델로 받아온 List의 각 값들(요소)에 대해 인덱스를 부여하고 변수명 item으로 값을 반복처리
    	$.each(friendList, function(index, item){
    	    let result = '';
    	    result += index + ' : ' + item.friendshipId + ', ' + item.memberId + ', ' + item.friendMemberId + ', ' + item.friendState + ', ' + item.friendshipDate;
    	    console.log(result);

    	    // 온라인인 경우
    	    if (item.friendState === 'online') {
    	    	// 온라인친구 배열에 값을 추가
    	        onlineFriend.push(item);
    	    }
    	    // 오프라인인 경우
    	    else if (item.friendState === 'offline') {
    	    	// 오프라인친구 배열에 값 추가
    	        offlineFriend.push(item);
    	    }
    	});
    	
	    $.each(onlineFriend, function(index, friend){
	        $('#onlineFriend').append('<li class="online">' + friend.friendMemberId + '</li>');
	    });

	    $.each(offlineFriend, function(index, friend){
	        $('#offlineFriend').append('<li class="offline">' + friend.friendMemberId + '</li>');
	    });
	    
    	$(document).on('click', '#btnShowFriendList', function(){
    		$('#tabList').html('');
    	    let onlineListHTML = '';
    	    $.each(onlineFriend, function(index, friend){
    	        onlineListHTML += '<li class="online">' + friend.friendMemberId + '</li>';
    	    });

    	    let offlineListHTML = '';
    	    $.each(offlineFriend, function(index, friend){
    	        offlineListHTML += '<li class="offline">' + friend.friendMemberId + '</li>';
    	    });

    	    $('#tabList').html(
    	        '<div id="onlineOfflineTab" class="tab-content active">'
    	        + '<h3>온라인 친구</h3>'
    	        + '<ul class="friend-list" id="onlineFriend">'
    	        + onlineListHTML
    	        + '</ul>'
    	        + '<h3>오프라인 친구</h3>'
    	        + '<ul class="friend-list" id="offlineFriend">'
    	        + offlineListHTML
    	        + '</ul>'
    	        + '</div>'
    	    );
		});
    	
    	$(document).on('click', '#btnSendRequest', function(){
    		
    	});
    	
    	$(document).on('click', '#btnReceiveRequestList', function(){
    		
    	});
    	
    });
    </script>
</body>
</html>





