package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

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
