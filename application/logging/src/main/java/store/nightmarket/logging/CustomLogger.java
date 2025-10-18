package store.nightmarket.logging;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.logstash.logback.argument.StructuredArguments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import store.nightmarket.logging.model.CommunicationLog;

public class CustomLogger {

	private static final Logger log = LoggerFactory.getLogger(CustomLogger.class);
	private static final ObjectMapper objectMapper = new ObjectMapper()
		.registerModule(new JavaTimeModule());

	public static void log(CommunicationLog communicationLog) {
		Map<String, Object> map = objectMapper.convertValue(communicationLog, new TypeReference<>() {
		});
		log.info(communicationLog.getEventType().toString(), StructuredArguments.value("event", map));
	}

}
