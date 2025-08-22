package store.nightmarket.application.apporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	"store.nightmarket.application.apporder",
	"store.nightmarket.domain.order",
	"store.nightmarket.persistence.persistorder"
})
public class AppOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppOrderApplication.class, args);
	}

}
