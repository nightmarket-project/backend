package store.nightmarket.application.appitem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "scheduling")
public class SchedulingProperties {

	private int batchPeriod;
	private int batchChunkSize;
	private String batchCron;

}
