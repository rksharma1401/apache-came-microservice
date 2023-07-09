package learn.camelmicorservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {
 
	@Override
	public void configure() throws Exception {
int i=0;
		from("timer:wss-timer?period=5000").transform().constant("{\"name\":\"new"+(++i)+"\"" )
			.log("${body}").to("websocket:wss://spring-ou5b.onrender.com/web-socket-endpoint");

	}
}
