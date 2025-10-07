package store.nightmarket.application.appuser.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import store.nightmarket.application.appuser.auth.logging.FeignLogger;

@Configuration
public class FeignConfig {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public Logger feignLogger() {
		return new FeignLogger();
	}

}
