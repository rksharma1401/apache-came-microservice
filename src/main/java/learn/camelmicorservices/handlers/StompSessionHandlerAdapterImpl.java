package learn.camelmicorservices.handlers;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class StompSessionHandlerAdapterImpl extends StompSessionHandlerAdapter {
 
	@Override
	public Type getPayloadType(StompHeaders headers) {
		return String.class;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/topic/stringResponse", new StompFrameHandler() {

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				String str = (String) payload;
				System.out.println("Received repsonse of response:" + str);
			}

			@Override
			public Type getPayloadType(StompHeaders headers) {
				return String.class;
			}
		});
		
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		exception.printStackTrace();
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		exception.printStackTrace();
	}
 
}
