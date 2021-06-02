package learn.camelmicorservices.routes;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	@Autowired
	private SimpleLogProcessorCommand simpleLogProcessorCommand;
	@Autowired
	private SimpleProcessor simpleProcessor;

	@Override
	public void configure() throws Exception {
		from("timer:first-timer").transform().constant("My message :" + LocalDateTime.now()).log("${body}")
				.bean(getCurrentTimeBean, "getCurrentTime").log("${body}").bean(simpleLogProcessorCommand)
				.log("${body}").bean(simpleProcessor).to("log:first-timer");

	}

}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Current time is : " + LocalDateTime.now();
	}
}

@Component
class SimpleLogProcessorCommand {
	private Logger log = LoggerFactory.getLogger(SimpleLogProcessorCommand.class);

	public void process(String message) {
		log.error("SimpleProcessorCommand {}", message);
	}
}

@Component
class SimpleProcessor implements Processor {
	private Logger log = LoggerFactory.getLogger(SimpleProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		log.error("SimpleProcessor {}", exchange.getMessage().getBody());
	}

}
