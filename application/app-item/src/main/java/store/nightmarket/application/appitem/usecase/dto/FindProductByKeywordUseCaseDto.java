package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.itemweb.model.ImageManager;

public class FindProductByKeywordUseCaseDto {

	@Builder
	public record Input(
		String keyword,
		int page
	) {

	}

	@Builder
	public record Output(
		Page<ProductPostAdapterDto> dtoPage,
		List<ImageManager> imageManagerList
	) {

	}
}
