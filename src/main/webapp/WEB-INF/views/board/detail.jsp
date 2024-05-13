	<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<!DOCTYPE html>
	<html>
	<head>
	<!-- jquery 라이브러리 import -->
	<script src="https://code.jquery.com/jquery-3.7.1.js">
		
	</script>
	<meta charset="UTF-8">
	<title>${boardVO.boardTitle }</title>
	</head>
	<body>
		<h2>글 보기</h2>
		<div>
			<p>글 번호 : ${boardVO.boardId }</p>
		</div>
		<div>
			<p>제목 :</p>
			<p>${boardVO.boardTitle }</p>
		</div>
		<div>
			<p>작성자 : ${boardVO.memberId }</p>
			<!-- boardDateCreated 데이터 포멧 변경 -->
			<fmt:formatDate value="${boardVO.boardRegistDate }"
				pattern="yyyy-MM-dd HH:mm:ss" var="boardRegistDate" />
			<p>작성일 : ${boardRegistDate }</p>
		</div>
		<div>
			<textarea rows="20" cols="120" readonly>${boardVO.boardContent }</textarea>
		</div>
	
		<p>
			<a href="download?attachId=${attachVO.attachId }">
				${attachVO.attachRealName }.${attachVO.attachExtension }</a>
		</p>
	
	
		<button onclick="location.href='list'">글 목록</button>
		<button onclick="location.href='update?boardId=${boardVO.boardId}'">글
			수정</button>
		<button id="deleteBoard">글 삭제</button>
		<form id="deleteForm" action="delete" method="POST">
			<input type="hidden" name="boardId" value="${boardVO.boardId }">
		</form>
	
	
		<script type="text/javascript">
			$(document).ready(function() {
				$('#deleteBoard').click(function() {
					if (confirm('삭제하시겠습니까?')) {
						$('#deleteForm').submit(); // form 데이터 전송
					}
				});
			}); // end document
		</script>
		<input type="hidden" id="boardId" value="${boardVO.boardId}">
	
		<hr>
		<p3>댓글작성</p3>
		<div style="text-align: left;">
			<input type="text" id="memberId"> 
			<input type="text" id="replyContent">
			<button id="btnAdd">작성</button>
		</div>
	
		<hr>
		<div style="text-align: left;">
			<div id="replies"></div>
			<div id="rereplies"></div>				
		</div>
		<hr>
	
	
		<script type="text/javascript">
			$(document).ready(function() {
				
				getAllReply(); // 함수 호출      
				
				$('#btnAdd').click(function() {
					let boardId = $('#boardId').val(); // 게시판 번호 데이터
					let memberId = $('#memberId').val(); // 작성자 데이터
					let replyContent = $('#replyContent').val(); // 댓글 내용
					let obj = {
						'boardId' : boardId,
						'memberId' : memberId,
						'replyContent' : replyContent
					}
					console.log(obj);
					$.ajax({
						type : 'POST', // 메서드 타입
						url : '../reply', // url
						headers : { // 헤더 정보
							'Content-Type' : 'application/json' // json content-type 설정
						},
						data : JSON.stringify(obj), // JSON으로 변환
						success : function(result) { // 전송 성공 시 서버에서 result 값 전송
						console.log(result);
						if (result == 1) {
						alert('댓글 입력 성공');
						getAllReply(); // 함수 호출      
						}
					}
						});
					}); // end btnAdd.click()
					function getAllReply() {
					    let boardId = $('#boardId').val();
					    let url = '../reply/' + boardId;
	
					    $.getJSON(url, function(data) {
					        let list = '';
	
					        $(data).each(function() {
					            let replyRegistDate = new Date(this.replyRegistDate);
	
					            list += '<div class="reply_item">'
					                + '<pre>'
					                + '<input type="hidden" class="replyId" value="'+ this.replyId +'">'
					                + this.memberId
					                + '&nbsp;&nbsp;'
					                + '<input type="text" class="replyContent" value="'+ this.replyContent +'">'
					                + '&nbsp;&nbsp;'
					                + replyRegistDate
					                + '&nbsp;&nbsp;'
					                + '<button class="btn_update" >수정</button>'
					                + '<button class="btn_delete" >삭제</button>'
					                + '<button class="btn_add">대댓글작성</button>'
					                + '</pre>'
					                + '</div>';
	
					            // 여기서 각 댓글에 해당하는 rereply를 가져오도록 설정합니다.
					            getAllRereply(this.replyId);
					        });
	
					        $('#replies').html(list);
					    });
					}
					
					function getAllRereply(replyId) {
					    let url = '../rereply/' + replyId;
	
					    $.getJSON(url, function(data) {
					        let list = '';
	
					        $(data).each(function() {
					            let rereplyRegistDate = new Date(this.rereplyRegistDate);
	
					            list += '<div class="rereply_item">'
					                + '<pre> &nbsp;&nbsp;&nbsp;&#8627;&nbsp;&nbsp;'
					                + '<input type="hidden" class="rereplyId" value="'+ this.rereplyId +'">'
					                + this.memberId
					                + '&nbsp;&nbsp;' // 공백
					                + '<input type="text" class="rereplyContent" value="'+ this.rereplyContent +'">'
					                + '&nbsp;&nbsp;'
					                + rereplyRegistDate
					                + '&nbsp;&nbsp;'
					                + '<button class="btn_update" >수정</button>'
					                + '<button class="btn_delete" >삭제</button>'
					                + '</pre>'
					                + '</div>';
					        });
	
					        // 해당 댓글 아래에 rereply를 추가합니다.
					        $('#replies').find('.reply_item input.replyId[value="' + replyId + '"]').closest('.reply_item').after(list);
					    });
					}			
	
					 // 대댓글 작성 버튼 클릭 이벤트 핸들러
				    $('#replies').on('click', '.reply_item .btn_add', function() {
				        // 대댓글 작성을 위한 입력 필드와 작성 버튼을 추가
				        let rereplyInputFields = 
				            '<div style="text-align: center;">' +
				                '<input type="text" class="rereply_memberId" placeholder="작성자">' +
				                '<input type="text" class="rereply_content" placeholder="대댓글 내용">' +
				                '<button class="btnAddRereply">작성</button>' +
				            '</div>';
	
				        $(this).closest('.reply_item').append(rereplyInputFields);
				    });
	
				    // 대댓글 작성 버튼 클릭 이벤트 핸들러
				    $('#replies').on('click', '.reply_item .btnAddRereply', function() {
				        // 대댓글 작성 버튼이 속한 부모 댓글의 ID 가져오기
				        let replyId = $(this).closest('.reply_item').find('.replyId').val();
				        
				        // 대댓글 작성을 위한 입력된 내용 가져오기
				        let memberId = $(this).closest('.reply_item').find('.rereply_memberId').val();
				        let rereplyContent = $(this).closest('.reply_item').find('.rereply_content').val();
				        console.log(replyId);
				        console.log(memberId);
				        console.log(rereplyContent);
	
				        // 대댓글 작성 정보를 객체로 생성
				        let obj = {
				            'replyId': replyId,
				            'memberId': memberId,
				            'rereplyContent': rereplyContent
				        };
						console.log(obj);
				        // 대댓글 작성 요청을 서버에 전송
				        $.ajax({
				            type: 'POST',
				            url: '../rereply',
				            headers: {
				                'Content-Type': 'application/json'
				            },
				            data: JSON.stringify(obj),
				            success: function(result) {
				                console.log(result);
				                if (result == 1) {
				                    alert('대댓글 입력 성공');
				                    // 대댓글 작성 후에는 해당 댓글의 대댓글 목록을 다시 가져옴
				                    getAllReply();
				                }
				            }
				        });
				    });
				    
				    $('#replies').on('click','.reply_item .btn_update',
				    	function() {
				    		console.log(this);
							let replyId = $(this).closest('.reply_item').find('.replyId').val();//$(this).prevAll('#replyId').val();
							let replyContent = $(this).closest('.reply_item').find('.replyContent').val();//$(this).prevAll('#replyContent').val();
							console.log("선택된 댓글 번호 : "	+ replyId+ ", 댓글 내용 : "+ replyContent);
							// ajax 요청
							$.ajax({
								type : 'PUT',
								url : '../reply/'+ replyId,
								headers : {
									'Content-Type' : 'application/json'
								},
								data : replyContent,
								success : function(result) {
									console.log(result);
									if (result == 1) {
										alert('댓글 수정 성공!');
										getAllReply();
										}
									}
									});
							}); // end replies.on()
							
					$('#replies').on('click', '.rereply_item .btn_update', 
						function() {
							console.log(this);
					    	let rereplyId = $(this).closest('.rereply_item').find('.rereplyId').val();
					    	let rereplyContent = $(this).closest('.rereply_item').find('.rereplyContent').val();
							console.log("선택된 대댓글 번호 "+rereplyId+" 대댓글내용 = "+rereplyContent);
					    	$.ajax({
						        type: 'PUT',
						        url: '../rereply/' + rereplyId,
						        headers: {
						            'Content-Type': 'application/json'
						        },
					    	    data: rereplyContent,
					        	success: function(result) {
					            	console.log(result);
					            if (result == 1) {
					                alert('대댓글 수정 성공!');
					                getAllReply();
					                
					                //location.reload();
					            }
					        }
					    });
					});		
				
							
					$('#replies').on('click','.reply_item .btn_delete',function() {
							console.log(this);
							let boardId = $('#boardId').val(); // 게시판 번호 데이터
							let replyId = $(this).closest('.reply_item').find('.replyId').val();
							
							console.log("boardId : "+boardId+" replyId : "+replyId);
							$.ajax({
						        type: 'DELETE',
						        url: '../rereply/' + replyId+'/'+boardId,
						        headers: {
						            'Content-Type': 'application/json'
						        },
						        success: function(result) {
						            console.log(result);
						            if (result == 1) {
						               console.log('대댓글 삭제 성공!');
						               $.ajax({
											type : 'DELETE',
											url : '../reply/'+ replyId + '/' + boardId,
											headers : {
												'Content-Type' : 'application/json'
											},
											success : function(result) {
												console.log(result);
												if (result == 1) {
													alert('댓글 삭제 성공');
													
												    getAllReply();
												}
											}
										});
						            }
						        }
						    });
							
						}); // end replies.on()      
				}); // end document()
					
					
	
					// 대댓글 삭제 버튼 클릭 이벤트 핸들러
					$('#replies').on('click', '.rereply_item .btn_delete', function() {
					    let rereplyId = $(this).closest('.rereply_item').find('.rereplyId').val();
					    let replyId = $(this).closest('.reply_item').find('.replyId').val();//$(this).prevAll('#replyId').val();
					    $.ajax({
					        type: 'DELETE',
					        url: '../rereply/' + rereplyId,
					        headers: {
					            'Content-Type': 'application/json'
					        },
					        success: function(result) {
					            console.log(result);
					            if (result == 1) {
					                alert('대댓글 삭제 성공!');
					                location.reload();
					               
					            }
					        }
					    });
					});
					
		</script>
	
	</body>
	</html>
	
	
	
