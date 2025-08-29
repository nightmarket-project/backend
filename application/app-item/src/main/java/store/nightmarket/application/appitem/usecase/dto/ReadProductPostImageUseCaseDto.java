package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;

public class ReadProductPostImageUseCaseDto {

	@Builder
	public record Input(
		UUID id,
		List<DomainImageType> imageTypeList
	) {

	}

	@Builder
	public record Output(
		List<ImageManager> imageManagerList
	) {

	}

}
