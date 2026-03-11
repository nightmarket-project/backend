package store.nightmarket.application.appuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {
	"store.nightmarket.application.appuser",
	"store.nightmarket.domain.user",
	"store.nightmarket.persistence.persistuser",
	"store.nightmarket.application.appoutbox",
	"store.nightmarket.domain.outbox",
	"store.nightmarket.persistence.persistoutbox"
})
@EnableJpaRepositories(basePackages = {
	"store.nightmarket.persistence.persistuser",
	"store.nightmarket.persistence.persistoutbox",
})
@EntityScan(basePackages = {
	"store.nightmarket.persistence.persistuser",
	"store.nightmarket.persistence.persistoutbox",
})
@EnableRedisHttpSession
@EnableScheduling
public class AppUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUserApplication.class, args);
	}

}
