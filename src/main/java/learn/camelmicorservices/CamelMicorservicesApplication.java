package learn.camelmicorservices;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import learn.camelmicorservices.handlers.StompSessionHandlerAdapterImpl;

@SpringBootApplication
@EnableScheduling
@EnableWebSocket
@Controller
public class CamelMicorservicesApplication {
	
	@GetMapping("isWorking")
	public ResponseEntity<String> isWorking() {
		return ResponseEntity.ok("Running");

	}

	public static void main(String[] args) {
		SpringApplication.run(CamelMicorservicesApplication.class, args);

	}

	private SSLContext ignoreCertificates() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			Map<String, Object> properties = new HashMap<>();
			properties.put("org.apache.tomcat.websocket.SSL_CONTEXT", sc);
			return sc;
		} catch (Exception e) {

		}
		return null;
	}

	@Bean
	public WebSocketStompClient webSocketClientOld() {
		StandardWebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
		Map<String, Object> userProperties = new HashMap<>();
		userProperties.put("org.apache.tomcat.websocket.SSL_CONTEXT", ignoreCertificates());
		userProperties.put("transports", "websocket");
		simpleWebSocketClient.setUserProperties(userProperties);

		final WebSocketStompClient stompClient = new WebSocketStompClient(simpleWebSocketClient);

		List<MessageConverter> converters = new ArrayList<>();
		converters.add(new MappingJackson2MessageConverter()); // used to handle json messages
		converters.add(new StringMessageConverter()); // used to handle raw strings

		stompClient.setMessageConverter(new CompositeMessageConverter(converters));
		return stompClient;
	}

}
