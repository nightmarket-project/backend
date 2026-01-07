package store.nightmarket.application.appitem.usecase.post.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ImageOwnerId;

public class ReadImageManagerListUseCaseDto {

	@Builder
	public record Input(
		List<ImageOwnerId> idList
	) {

	}

	@Builder
	public record Output(
		List<ImageManager> imageManagerList
	) {

	}

}
