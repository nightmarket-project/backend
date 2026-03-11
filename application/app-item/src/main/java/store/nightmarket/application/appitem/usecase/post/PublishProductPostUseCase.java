package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.PublishProductPostUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.ProductPost;

@Service
@RequiredArgsConstructor
public class PublishProductPostUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPostPort readProductPostPort;
	private final SaveProductPostPort saveProductPostPort;

	@Override
	public Void execute(Input input) {
		ProductPost productPost = readProductPostPort.readOrThrow(input.productPostId());

		productPost.publish();

		saveProductPostPort.save(productPost);
		return null;
	}

}
