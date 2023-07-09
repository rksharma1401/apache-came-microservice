package learn.camelmicorservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMqReaderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("/topic/response").log("${body}").to("/topic/response2");
	}
}
