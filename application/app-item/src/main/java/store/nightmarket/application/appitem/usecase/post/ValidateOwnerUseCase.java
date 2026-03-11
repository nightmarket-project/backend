package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ValidateOwnerUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.mapper.dto.ProductPostAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.exception.ProductPostException;

@Service
@RequiredArgsConstructor
public class ValidateOwnerUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPostPort readProductPostPort;

	@Override
	public Void execute(Input input) {
		ProductPostAdapterDto productPostAdapterDto = readProductPostPort.readOrThrowFetch(input.productPostId());

		if (!productPostAdapterDto.getProduct().isOwner(input.userId())) {
			throw new ProductPostException("Not Owner For ProductPost");
		}

		return null;
	}

}
