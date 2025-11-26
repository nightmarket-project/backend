package store.nightmarket.application.appitem.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import redis.embedded.RedisServer;

@Configuration
@Profile("test")
public class EmbeddedRedisConfig {

	@Value("${spring.data.redis.port}")
	private int redisPort;

	private RedisServer redisServer;

	@PostConstruct
	public void redisServer() throws IOException {
		redisServer = new RedisServer(redisPort);
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		if (redisServer != null) {
			redisServer.stop();
		}
	}

}