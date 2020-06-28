package villasboas.eduardo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import villasboas.eduardo.websocket.Greeting;
import villasboas.eduardo.websocket.HelloMessage;

@Controller
public class GreetingController {

	//Testar SimpMessagingTemplate 
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(5000);
		return new Greeting("Hello " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

}
