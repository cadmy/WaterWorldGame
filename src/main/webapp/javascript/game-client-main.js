window.addEventListener("load", start, false);
var webSocket;
var canvas;
var context

function start(){
	writeToScreen("Start");
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
	
	canvas= document.getElementById('myCanvas');
	context = canvas.getContext('2d');
	  
	canvas.addEventListener('mousedown', function(evt) {
		var mousePos = getMousePos(canvas, evt);
		var position = '{'
						   + '"x" : ' + mousePos.x
						   + ', "y" : ' + mousePos.y
						   + '}';
		doSend(position);
	}, false);
}	

function onOpen(event){
	writeToScreen("Connection established");
}

function onClose(event){
	writeToScreen("The connection was interrupted T.T");
}

function onMessage(event){
	var json = JSON.parse(event.data);
	writeToScreen("Player: " + json.sessionName + "; x = " + json.x + "; y = " + json.y);
}

function onError(event){
	writeToScreen('<span style="color: red;">ERROR:</span> ' + event.data);
}	

function writeToScreen(message) {
	document.getElementById('messages').innerHTML += message + "<br>";
}		

function doSend(position){
	writeToScreen("SENT: " + position);
	webSocket.send(position);
}

function getMousePos(canvas, event) {
	var rect = canvas.getBoundingClientRect();
	return {
	  x: event.clientX - rect.left,
	  y: event.clientY - rect.top
	};
}

