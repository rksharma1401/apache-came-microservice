package learn.camelmicorservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReaderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("rabbitmq:my-queue").log("${body}").to("rabbitmq:sec-queue");
		from("rabbitmq:sec-queue").log("second").log("${body}").to("rabbitmq:third-queue")
	;}
}
