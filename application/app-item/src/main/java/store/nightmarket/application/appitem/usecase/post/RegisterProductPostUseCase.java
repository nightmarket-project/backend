package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.RegisterProductPostUseCaseDto.*;

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
import store.nightmarket.domain.itemweb.valueobject.Rating;

@Service
@RequiredArgsConstructor
public class RegisterProductPostUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final SaveProductPostPort saveProductPostPort;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new ProductPostException("Not Owner For Product");
		}

		ProductPost productPost = ProductPost.newInstance(
			new ProductPostId(UUID.randomUUID()),
			input.productId(),
			new Rating(0.0f),
			PostState.UNPUBLISHED
		);

		saveProductPostPort.save(productPost);
		return null;
	}
	
}
