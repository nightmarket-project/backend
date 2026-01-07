package store.nightmarket.application.appitem.usecase.post.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageType;

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
