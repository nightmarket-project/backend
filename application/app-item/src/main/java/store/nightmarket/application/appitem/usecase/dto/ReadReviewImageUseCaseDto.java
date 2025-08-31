package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.itemweb.model.ImageManager;

public class ReadReviewImageUseCaseDto {

	@Builder
	public record Input(
		List<UUID> idList
	) {

	}

	@Builder
	public record Output(
		List<ImageManager> imageManagerList
	) {

	}

}
