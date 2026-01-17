package store.nightmarket.application.appitem.usecase.post.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.mapper.dto.ReviewAdapterDto;

public class ReadReviewUseCaseDto {

	@Builder
	public record Output(
		List<ReviewAdapterDto> reviewAdapterDtoList
	) {

	}

}
