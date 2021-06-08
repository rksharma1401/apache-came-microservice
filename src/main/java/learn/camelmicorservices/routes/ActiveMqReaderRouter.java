package learn.camelmicorservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReaderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("activemq:my-queue").log("${body}").to("activemq:sec-queue");
from("activemq:sec-queue").log("second").log("${body}").to("activemq:third-queue")
	}
}
