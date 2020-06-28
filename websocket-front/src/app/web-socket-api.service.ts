import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { Injectable } from '@angular/core';

export interface MessageHandler {
    connect(): void;
    handleMessage(message: any): void;
}

@Injectable()
export class WebSocketApiService {

    webSocketEntPoint: string = 'http://localhost:8080/ws';
    topic: string = '/topic/greetings';

    _connect(messageHandler: MessageHandler) {
        console.log("Initialize WebSocket Connection");
        this._disconnect(messageHandler);
        let ws = new SockJS(this.webSocketEntPoint);
        messageHandler['stompClient'] = Stomp.over(ws);
        const _this = this;
        messageHandler['stompClient'].connect({}, function (_frame) {
            messageHandler['stompClient'].subscribe(_this.topic, function (sdkEvent) {
                _this.onMessageReceived(messageHandler, sdkEvent);
            });
        }, this.errorCallBack.bind(this, messageHandler));

    }

    _disconnect(messageHandler: MessageHandler) {
        if (messageHandler['stompClient']) {
            messageHandler['stompClient'].disconnect();
            messageHandler['stompClient'] = undefined;
        }

        console.log('Disconnected');
    }

    errorCallBack(messageHandler: MessageHandler) {
        setTimeout(() => {
            this._connect(messageHandler);
        }, 5000);
    }

    _send(messageHandler: MessageHandler, message) {
        console.log('calling logout api via web socket');
        messageHandler['stompClient'].send('/app/hello', {}, JSON.stringify(message));
    }

    onMessageReceived(messageHandler: MessageHandler, message) {
        console.log('Message Received from Server -> ', message);
        messageHandler.handleMessage(JSON.stringify(message.body));
    }
}
