import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Injectable } from '@angular/core';

interface MessageHandler {
    connect(): void;
    handleMessage(message: any): void;
}

@Injectable()
export class WebSocketApiService {

    webSocketEntPoint: string = 'http://localhost:8080/ws';
    topic: string = '/topic/greetings';
    stompClient: any;

    _connect(messageHandler: MessageHandler) {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEntPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (_frame) {
            _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
                _this.onMessageReceived(sdkEvent, messageHandler);
            });
        }, this.errorCallBack.bind(this, messageHandler));

    }

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }

        console.log('Disconnected');
    }

    errorCallBack(error) {
        console.error('error: ', error);
        setTimeout(() => {
            console.log(this, arguments);
            this._connect(arguments[0]);
        }, 5000);
    }

    _send(message) {
        console.log('calling logout api via web socket');
        this.stompClient.send('/app/hello', {}, JSON.stringify(message));
    }

    onMessageReceived(message, messageHandler: MessageHandler) {
        console.log('Message Received from Server -> ', message);
        messageHandler.handleMessage(JSON.stringify(message.body));
    }
}
