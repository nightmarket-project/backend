package store.nightmarket.application.appitem.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.in.dto.RegisterOptionDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductDto;
import store.nightmarket.application.appitem.usecase.RegisterOptionUseCase;
import store.nightmarket.application.appitem.usecase.RegisterProductUseCase;
import store.nightmarket.application.appitem.usecase.dto.RegisterOptionUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.RegisterProductUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

@RestController
@RequestMapping("api/v1/admin/product")
@RequiredArgsConstructor
public class AdminProductControllerV1 {

	private final RegisterProductUseCase registerProductUseCase;
	private final RegisterOptionUseCase registerOptionUseCase;

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_BUYER"})
	public void registerProduct(@RequestBody RegisterProductDto.Request request) {
		registerProductUseCase.execute(
			RegisterProductUseCaseDto.Input.builder()
				.name(new Name(request.name()))
				.description(request.description())
				.price(new Price(request.price()))
				.build()
		);
	}

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_BUYER"})
	public void registerProduct(@RequestBody RegisterOptionDto.Request request) {
		registerOptionUseCase.execute(
			RegisterOptionUseCaseDto.Input.builder()
				.productId(new ProductId(request.productId()))
				.name(new Name(request.name()))
				.displayOrder(request.displayOrder())
				.optionValueDtoList(
					request.optionValueDtoList().stream()
						.map(requestList ->
							RegisterOptionUseCaseDto.OptionValueDto.builder()
								.name(new Name(requestList.name()))
								.price(new Price(requestList.price()))
								.displayOrder(requestList.displayOrder())
								.build()
						)
						.toList()
				)
				.build()
		);
	}
}
