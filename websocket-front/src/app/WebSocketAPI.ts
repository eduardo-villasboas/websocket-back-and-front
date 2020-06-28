import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { AppComponent } from './app.component';

export class WebSocketAPI {

    webSocketEntPoint: string = 'http://localhost:8080/ws';
    topic: string = '/topic/greetings';
    stompClient: any;
    appComponent: AppComponent;

    constructor(appComponent: AppComponent) {
        this.appComponent = appComponent;
    }

    _connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEntPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (_frame) {
            _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
                _this.onMessageReceived(sdkEvent);
            });
            //_this.stompClient.reconnect_delay = 2000;
        }, this.errorCallBack);


    }

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }

        console.log('Disconnected');
    }

    errorCallBack(error) {
        console.log('errorCallBack -> ', error);
        setTimeout(() => {
            this.appComponent.connect();
        }, 5000);
    }

    _send(message) {
        console.log('calling logout api via web socket');
        this.stompClient.send('/app/hello', {}, JSON.stringify(message));
    }

    onMessageReceived(message) {
        console.log('Message Received from Server -> ', message);
        this.appComponent.handleMessage(JSON.stringify(message.body));
    }
}
