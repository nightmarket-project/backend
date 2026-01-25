package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.RegisterAndScheduleProductPostUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.PostState;
import store.nightmarket.domain.itemweb.service.ScheduleProductPostDomainService;
import store.nightmarket.domain.itemweb.service.dto.ScheduleProductPostDomainServiceDto;
import store.nightmarket.domain.itemweb.valueobject.Rating;

@Service
@RequiredArgsConstructor
public class RegisterAndScheduleProductPostUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final SaveProductPostPort saveProductPostPort;
	private final ScheduleProductPostDomainService scheduleProductPostDomainService;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new ProductPostException("Not Owner For Product");
		}

		ProductPost productPost = ProductPost.newInstance(
			new ProductPostId(UUID.randomUUID()),
			product.getProductId(),
			new Rating(5.0F),
			PostState.DRAFT
		);

		ScheduleProductPostDomainServiceDto.Event event = scheduleProductPostDomainService.execute(
			ScheduleProductPostDomainServiceDto.Input.builder()
				.productPost(productPost)
				.publishAt(input.publishAt())
				.expiredAt(input.expiredAt())
				.build()
		);

		saveProductPostPort.save(event.getProductPost());
		return null;
	}

}
