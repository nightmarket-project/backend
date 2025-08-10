package store.nightmarket.application.appuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {
	"store.nightmarket.application.appuser",
	"store.nightmarket.domain.user",
	"store.nightmarket.persistence.persistuser"
})
@EnableJpaRepositories(basePackages = "store.nightmarket.persistence.persistuser")
@EntityScan(basePackages = "store.nightmarket.persistence.persistuser")
@EnableRedisHttpSession
public class AppUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUserApplication.class, args);
	}

}
