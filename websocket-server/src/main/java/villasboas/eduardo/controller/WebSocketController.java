package villasboas.eduardo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import villasboas.eduardo.websocket.ResponseMessageDto;
import villasboas.eduardo.websocket.RequestMessageDto;

@Controller
public class WebSocketController {

	private static final int ONE_SECONDS_IN_MILISECONDS = 1000;


	private final SimpMessagingTemplate simpMessagingTemplate;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);


	public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@MessageMapping("/hello")
	public void onReceive(RequestMessageDto message) throws Exception {
		LOGGER.trace("creating thread.");
		Thread threadToSend = new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					LOGGER.trace("Waiting for {} miliseconds", ONE_SECONDS_IN_MILISECONDS);
					Thread.sleep(ONE_SECONDS_IN_MILISECONDS);
				} catch (InterruptedException e) {
					LOGGER.error("Error when waiting thread for {} miliseconds", ONE_SECONDS_IN_MILISECONDS);
				}
				LOGGER.trace("sending message.");
				simpMessagingTemplate.convertAndSend("/topic/greetings",
						new ResponseMessageDto("Hello " + HtmlUtils.htmlEscape(message.getName()) + "!"));
				LOGGER.trace("message was sent.");
			}

		});
		LOGGER.trace("Thread was created {}", threadToSend);
		threadToSend.start();
		LOGGER.trace("Thead was started.");
	}

}
