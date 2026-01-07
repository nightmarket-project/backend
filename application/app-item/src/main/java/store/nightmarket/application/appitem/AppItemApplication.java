package store.nightmarket.application.appitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "store.nightmarket.persistence.persistitem")
@EntityScan(basePackages = "store.nightmarket.persistence.persistitem")
@ComponentScan(basePackages = {
	"store.nightmarket.persistence.persistitem",
	"store.nightmarket.application.appitem",
	"store.nightmarket.domain.itemweb",
	"store.nightmarket.domain.item"
})
@EnableRedisHttpSession
public class AppItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppItemApplication.class, args);
	}

}
