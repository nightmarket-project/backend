package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadReviewUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadReviewPort;
import store.nightmarket.application.appitem.out.dto.ReviewAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class ReadReviewUseCase implements BaseUseCase<UUID, Output> {

	private final ReadReviewPort readReviewPort;

	@Override
	public Output execute(UUID productPostId) {
		List<ReviewAdapterDto> reviewAdapterDtoList = readReviewPort.read(productPostId);

		return Output.builder()
			.reviewAdapterDtoList(reviewAdapterDtoList)
			.build();
	}

}
