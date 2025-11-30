package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.state.ImageType;
import store.nightmarket.itemweb.model.id.ProductPostId;

public class ReadProductPostImageUseCaseDto {

	@Builder
	public record Input(
		ProductPostId id,
		List<ImageType> imageTypeList
	) {

	}

	@Builder
	public record Output(
		List<ImageManager> imageManagerList
	) {

	}

}
