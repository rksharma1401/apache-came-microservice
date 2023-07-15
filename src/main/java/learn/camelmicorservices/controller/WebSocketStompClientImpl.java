package learn.camelmicorservices.controller;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import learn.camelmicorservices.handlers.StompSessionHandlerAdapterImpl;
import learn.camelmicorservices.model.StoreMessage;

@Component(value = "webSocketStompClientImpl")
public class WebSocketStompClientImpl {

	private StompSession stompSession;

	@Autowired
	StompSessionHandlerAdapterImpl handlerImpl;
	@Autowired
	WebSocketStompClient stompClient;
	@Value("${websocket}")
	String url;

	public WebSocketStompClientImpl(@Autowired StompSessionHandlerAdapterImpl handlerImpl, @Autowired WebSocketStompClient stompClient, @Value("${websocket}") String url)
			throws InterruptedException, ExecutionException {
		stompSession = stompClient.connect(url, handlerImpl).get();
	}

	public void reconnect() throws InterruptedException, ExecutionException {
		this.stompSession = stompClient.connect(url, handlerImpl).get();
	}

	// @Scheduled(fixedDelay = 5000)
	public void sendMessage() throws InterruptedException, ExecutionException {

		if (stompSession.isConnected()) {
			StoreMessage storeMessage = new StoreMessage("Ravi", "Hello", LocalDateTime.now().toString());
			stompSession.send("/app/stringMessage", storeMessage.toString());
			System.out.println("message send");
		} else {
			System.out.println("reconnect no message send");
			try{reconnect();}catch(Exception e){System.out.println("reconnecting issue :- "+e.getMessage();}
		}

	}

}
