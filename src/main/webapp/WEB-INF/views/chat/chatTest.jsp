<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Chat</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
    <script type="text/javascript">
        var stompClient = null;

        function connect() {
            var socket = new SockJS('<%= request.getContextPath() %>/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function(messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
        }

        function disconnect() {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function sendMessage() {
            var message = document.getElementById('message').value;
            stompClient.send("/app/chat", {}, JSON.stringify({'message': message}));
        }

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
            document.getElementById('response').innerHTML = '';
        }

        function showMessageOutput(messageOutput) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(messageOutput.message));
            response.appendChild(p);
        }
    </script>
</head>
<body>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" onclick="disconnect();" disabled="disabled">Disconnect</button>
    </div>
    <div id="conversationDiv" style="visibility:hidden;">
        <input type="text" id="message" />
        <button id="send" onclick="sendMessage();">Send</button>
        <p id="response"></p>
    </div>
</body>
</html>
