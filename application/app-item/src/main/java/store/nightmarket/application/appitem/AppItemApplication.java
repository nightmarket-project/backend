package store.nightmarket.application.appitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
	"store.nightmarket.persistence.persistitem",
	"store.nightmarket.application.appitem"
	"store.nightmarket.application.appitem",
	"store.nightmarket.itemweb"
	"store.nightmarket.application.appitem"
})
public class AppItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppItemApplication.class, args);
	}

}
