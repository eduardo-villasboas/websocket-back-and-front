package villasboas.eduardo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import villasboas.eduardo.websocket.Greeting;
import villasboas.eduardo.websocket.HelloMessage;

@Controller
public class GreetingController {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public GreetingController(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@MessageMapping("/hello")
	public void greeting(HelloMessage message) throws Exception {
		Thread threadToSend = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				simpMessagingTemplate.convertAndSend("/topic/greetings",
						new Greeting("Hello " + HtmlUtils.htmlEscape(message.getName()) + "!"));
			}

		});
		threadToSend.start();
	}

}
