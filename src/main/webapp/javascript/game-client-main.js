window.addEventListener("load", start, false);
var webSocket;
var stage;
var players = [];
var shapes = [];
var userMarker;
var messageNumber = 0;
var userPosition;
var prevUserPosition;

function start(){
	writeToScreen("Start");
	webSocket = new WebSocket("ws://waterworldgame-cadmy.rhcloud.com:8000/WaterWorldGame/game_endpoint");
	
	webSocket.onopen = function(event) {
		onOpen(event);
	};
	
	webSocket.onerror = function(event) {
		onError(event);
	};

	webSocket.onmessage = function(event) {
		onMessage(event);
	};
	
	webSocket.onclose = function(event) {
		onClose(event);
	};

	stage = new createjs.Stage("myCanvas");
	createjs.MotionGuidePlugin.install();
	userMarker = new createjs.Shape();
	userMarker.graphics.beginFill("DeepSkyBlue").drawCircle(0, 0, 3);
	userMarker.x = -100;
    userMarker.y = -100;
	stage.on("stagemousedown", function(evt) {
		if (userMarker.x == -100 && userMarker.y == -100) {
			userMarker.x = evt.stageX;;
			userMarker.y = evt.stageY;
			stage.addChild(userMarker);
			stage.update();
		} else {
			createjs.Tween.get(userMarker, {override:true})
			.to({x:stage.mouseX, y:stage.mouseY}, 4000);
			createjs.Ticker.setFPS(20);	
		}
	});
	createjs.Ticker.addEventListener("tick", locate);
}	

function onOpen(event){
	writeToScreen("Connection established");
}

function onClose(event){
	writeToScreen("The connection was interrupted T.T");
}

function onMessage(event){
	writeToScreen("RECEIVED...");
	var json = JSON.parse(event.data);
    var isExist = false;

	for (var i = 0; i < players.length; i++) {
		if ( players[i] == json.playerName ) {
			shape = shapes[i];
			shape.x = json.x;
			shape.y = json.y;
			isExist = true;
			break;
		}
	}

    if (isExist == false)
    {
        players[players.length] = json.playerName;
		shapes[shapes.length] = new createjs.Shape();
		shape = shapes[shapes.length - 1];
        shape.graphics.beginFill("#555555").drawCircle(json.x, json.y, 3);
		stage.addChild(shape);
    }
	stage.update();
	writeToScreen("Player: " + json.sessionName + "; x = " + json.x + "; y = " + json.y);
}

function onError(event){
	writeToScreen('<span style="color: red;">ERROR:</span> ' + event.data);
}	

function writeToScreen(message) {
	document.getElementById('messages').innerHTML += message + "<br>";
	messageNumber++;
	if (messageNumber == 20){
	    document.getElementById('messages').innerHTML = "";
	    messageNumber = 0;
	}

}		

function doSend(x, y){
	userPosition = '{'
				   + '"x" : ' + Math.round(x)
				   + ', "y" : ' + Math.round(y)
				   + '}';	
	if (userPosition != undefined && x > 0 && y > 0 && userPosition != prevUserPosition)
	{	
		webSocket.send(userPosition);
		writeToScreen("SENT: " + userPosition);
		prevUserPosition = userPosition;
	}
}

function locate(event) {
	doSend(userMarker.x, userMarker.y);
	stage.update();		
}

