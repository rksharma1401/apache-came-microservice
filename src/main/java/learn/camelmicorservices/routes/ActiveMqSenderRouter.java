package learn.camelmicorservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {
 int i=0;
	@Override
	public void configure() throws Exception {
		from("timer:activemq-timer?period=5000").transform().constant("my message"+i++).log("${body}").to("activemq:my-queue");

	}
}
