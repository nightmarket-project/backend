package store.nightmarket.application.apporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "store.nightmarket.persistence.persistorder")
@EntityScan(basePackages = "store.nightmarket.persistence.persistorder")
@ComponentScan(basePackages = {
	"store.nightmarket.persistence.persistorder",
	"store.nightmarket.application.apporder",
	"store.nightmarket.domain.order"
})
public class AppOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppOrderApplication.class, args);
	}

}
