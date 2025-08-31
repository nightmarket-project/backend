package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.ReviewDto;

public class ReadReviewUseCaseDto {

	@Builder
	public record Output(
		List<ReviewDto> reviewDtoList
	) {

	}
	
}
