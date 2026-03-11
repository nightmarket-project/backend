package store.nightmarket.application.appitem.usecase.post;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.usecase.post.dto.UpdateProductPostUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.ProductPost;

@Service
@RequiredArgsConstructor
public class UpdateProductPostUseCase implements BaseUseCase<UpdateProductPostUseCaseDto.Input, Void> {

	private final ReadProductPostPort readProductPostPort;
	private final SaveProductPostPort saveProductPostPort;

	@Override
	public Void execute(UpdateProductPostUseCaseDto.Input input) {
		ProductPost productPost = readProductPostPort.readOrThrow(input.productPostId());

		productPost.edit(input.rating());

		saveProductPostPort.save(productPost);
		return null;
	}

}
