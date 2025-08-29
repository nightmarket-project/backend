package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.itemweb.state.DomainImageType;

public class ReadImageManagerUseCaseDto {

	@Builder
	public record Input(
		UUID id,
		List<DomainImageType> imageTypeList
	) {

	}

}
