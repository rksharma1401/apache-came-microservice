package learn.camelmicorservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("timer:wss-timer?period=2000").transform().constant("{\"name\":\"new" + "\"}")
		.log("Created" + "${body}")
		.to("bean:webSocketStompClientImpl?method=sendMessage");

	}
}
