window.addEventListener("load", start, false);
var webSocket

function start(){
	writeToScreen("Start <br>");
	webSocket = new WebSocket("ws://localhost:8080/WaterWorldGame/game_endpoint");

	webSocket.onopen = function(event) {
			onOpen(event);
	};
	webSocket.onerror = function(event) {
		onError(event);
	};

	webSocket.onmessage = function(event) {
		onMessage(event);
	};
}

function onOpen(evt){
	writeToScreen("Connection established <br>");
}

function onClose(evt){
	writeToScreen("The connection was interrupted T.T <br>");
}

function onMessage(evt){
	var json = JSON.parse(event.data);
	writeToScreen(json);
}

function onError(evt){
	writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}	

function writeToScreen(message) {
	document.getElementById('messages').innerHTML += message;
}		

function doSend(position){
	writeToScreen("SENT: " + position);
	websocket.send(message);
}