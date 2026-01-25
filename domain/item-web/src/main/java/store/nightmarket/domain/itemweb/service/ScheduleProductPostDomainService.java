package store.nightmarket.domain.itemweb.service;

import static store.nightmarket.domain.itemweb.service.dto.ScheduleProductPostDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.itemweb.model.ProductPost;

@Component
public class ScheduleProductPostDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		ProductPost productPost = input.getProductPost();

		productPost.schedule(
			input.getPublishAt(),
			input.getExpiredAt()
		);

		return Event.builder()
			.productPost(productPost)
			.build();
	}

}
