package villasboas.eduardo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import villasboas.eduardo.websocket.ResponseMessageDto;
import villasboas.eduardo.websocket.RequestMessageDto;

@Controller
public class WebSocketController {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@MessageMapping("/hello")
	public void onReceive(RequestMessageDto message) throws Exception {
		Thread threadToSend = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				simpMessagingTemplate.convertAndSend("/topic/greetings",
						new ResponseMessageDto("Hello " + HtmlUtils.htmlEscape(message.getName()) + "!"));
			}

		});
		threadToSend.start();
	}

}
