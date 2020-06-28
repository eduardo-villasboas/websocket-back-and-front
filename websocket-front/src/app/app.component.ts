import { Component, Input } from '@angular/core';
import { WebSocketApiService } from './web-socket-api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  title = 'websocket-front';

  greeting: any;
  
  name: string;

  constructor(private webSocketApiService: WebSocketApiService) {
  }

  connect() {
    this.webSocketApiService._connect(this);
  }

  disconnect() {
    this.webSocketApiService._disconnect();
  }

  sendMessage() {
    this.webSocketApiService._send(this.name);
  }

  handleMessage(message) {
    this.greeting = message;
  }

}
