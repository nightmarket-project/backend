package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ReadReviewUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.post.ReadReviewPort;
import store.nightmarket.application.appitem.out.post.mapper.dto.ReviewAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

@Service
@RequiredArgsConstructor
public class ReadReviewUseCase implements BaseUseCase<ProductPostId, Output> {

	private final ReadReviewPort readReviewPort;

	@Override
	public Output execute(ProductPostId productPostId) {
		List<ReviewAdapterDto> reviewAdapterDtoList = readReviewPort.read(productPostId);

		return Output.builder()
			.reviewAdapterDtoList(reviewAdapterDtoList)
			.build();
	}

}
