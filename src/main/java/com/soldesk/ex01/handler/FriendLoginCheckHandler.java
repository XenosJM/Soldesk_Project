package com.soldesk.ex01.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

public class FriendLoginCheckHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions;

    public FriendLoginCheckHandler(Map<String, WebSocketSession> sessions) {
        this.sessions = sessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트 연결 시 세션을 맵에 추가
        String userId = getUserId(session);
        sessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트 연결 종료 시 세션을 맵에서 제거
        String userId = getUserId(session);
        sessions.remove(userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 메시지 수신 시 처리 로직 (필요 시 구현)
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
    }

    public void sendLoginNotification(String userId, String message) throws Exception {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    private String getUserId(WebSocketSession session) {
        // 예를 들어, 세션에서 사용자 ID를 추출하는 방법 (JWT 토큰 등)
        // 여기서는 간단히 세션의 URI 쿼리 파라미터를 사용한다고 가정
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("userId=")) {
            return query.split("=")[1];
        }
        return null;
    }
}

//@Log4j
//public class AlarmHandler extends TextWebSocketHandler{
//
//	@Autowired
//	private ObjectMapper objectMapper;
//	
//	@Autowired
//	MessageService messageService;
//	
//	@Autowired
//	Map<String, WebSocketSession> alarmConnMap;
//	
//	private static final String TYPE_INSTANCE = "instanceAlarm";
//	private static final String TYPE_ALERT = "alert";
//	private static final String TYPE_AUTH_UPDATE = "authUpdateAlarm";
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////		log.info("socket message received : " + message.getPayload());
////		MessageVO messageVO = convertMsg(message.getPayload());
////		
////		MessageVO returnMsg = null;
////    	String msgType = messageVO.getType();
////    	String memberId = session.getPrincipal().getName();
////    	messageVO.setWriterId(memberId);
////    	
////    	if(msgType.equals(TYPE_ALERT)) {
////    		broadcast(messageVO);
////    	}
////		
//	} // end handleTextMessage
//	
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		log.info("socket open");
//		if(session.getPrincipal() == null) {
//			return;
//		}
//		String memberId = session.getPrincipal().getName();
//		log.info("연결 유저 id : " + memberId);
//		alarmConnMap.put(memberId, session);
//		log.info("접속 중인 유저 : " + alarmConnMap);
//		
//		List<MessageVO> messageList = messageService.getMyMessage(memberId);
//		// 연결이 끊어진 동안 못 받은 메시지 검색
//		messageService.removeByReceiverId(memberId);
//		// 가져온 메시지는 DB에서 삭제 (전송 실패시 다시 DB에 등록)
//		log.info("내 메세지 : " + messageList);
//		if(messageList != null) {
//			for(MessageVO vo : messageList) { // 전부 송신
//				vo.setReceiverId(memberId); // receiverId 값이 all인 메시지도 있으므로 receiver 재설정
//				unicast(vo);
//			}
//		}
//	} // end afterConnectionEstablished
//	
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		log.info("socket close");
//		String memberId = session.getPrincipal().getName();
//		log.info("연결 종료 유저 id : " + memberId);
//		alarmConnMap.remove(memberId);
//	} // end afterConnectionClosed
//	
//	
//	public void broadcast(MessageVO message) {
//		log.info("연결된 전체 유저에게 메시지 전송");
//		TextMessage jsonMsg = convertMsg(message);
//		
//		Iterator<String> iterator = alarmConnMap.keySet().iterator();
//
//		String receiverId; 
//		while(iterator.hasNext()) {
//			receiverId = iterator.next();
//			WebSocketSession client = alarmConnMap.get(receiverId);
//			if(client.isOpen()) {
//				try {
//					client.sendMessage(jsonMsg);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		} // end iterator
//		
//	} // end broadcast
//	
//	public void unicast(MessageVO message) {
//		log.info("단일 유저에게 메시지 전송");
//		String receiverId = message.getReceiverId();
//		TextMessage jsonMsg = convertMsg(message);
//		if(alarmConnMap.containsKey(receiverId)) { // 수신 대상이 접속 중이면 바로 송신
//			WebSocketSession client = alarmConnMap.get(receiverId);
//			if(client.isOpen()) {
//				try {
//					client.sendMessage(jsonMsg);
//					return;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		} // end 접속 중인 유저 송신
//		
//		// 접속 중이 아니면 db에 메시지 저장
//		log.info("저장할 메시지 정보 : " + message);
//		messageService.registerMessage(message);
//		
//	} // end unicast
//	
//	public void sendAuthUpdateAlarm(String memberId, String content) {
//		MessageVO returnMsg = new MessageVO();
//		returnMsg.setReceiverId(memberId);
//		returnMsg.setTitle("권한 변경 알림");
//		returnMsg.setContent(content);
//		returnMsg.setType(TYPE_AUTH_UPDATE);
//		unicast(returnMsg);
//	} // end sendAuthUpdateAlarm
//	
//	public void sendInstanceAlarm(String receiverId, String title, String content, String returnUri) {
//		MessageVO returnMsg = new MessageVO();
//		returnMsg.setType(TYPE_INSTANCE);
//		returnMsg.setTitle(title);
//		returnMsg.setContent(content);
//		returnMsg.setReceiverId(receiverId);
//		returnMsg.setCallbackInfo(returnUri);
//		unicast(returnMsg);
//	} // end sendInstanceAlarm
//	
//	public void sendInstanceAlarm(int productId, String title, String content, String returnUri) {
//		MessageVO returnMsg = new MessageVO();
//		String receiverId = messageService.getSellerIdOf(productId);
//		returnMsg.setType(TYPE_INSTANCE);
//		returnMsg.setTitle(title);
//		returnMsg.setContent(content);
//		returnMsg.setReceiverId(receiverId);
//		returnMsg.setCallbackInfo(returnUri);
//		unicast(returnMsg);
//	} // end sendInstanceAlarm
//	
//	private MessageVO convertMsg(String jsonMsg) {
//		MessageVO message = null;
//		try {
//			message = objectMapper.readValue(jsonMsg, MessageVO.class);
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return message;
//	} // end convertMsg
//	
//	private TextMessage convertMsg(MessageVO message) {
//		TextMessage jsonMsg = null;
//		try {
//			jsonMsg = new TextMessage(objectMapper.writeValueAsString(message));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonMsg;
//	} // end convertMsg
//	
//}

