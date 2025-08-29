package store.nightmarket.application.appitem.in;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ImageMangerControllerDto;
import store.nightmarket.application.appitem.in.dto.ReadProductPostControllerDto;
import store.nightmarket.application.appitem.usecase.ReadImageManagerUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadImageManagerUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ReadProductPostControllerV1 {

	private final ReadProductPostUseCase readProductPostUseCase;
	private final ReadImageManagerUseCase readImageManagerUseCase;

	@GetMapping("/{postId}")
	public ReadProductPostControllerDto.Response readProductPost(@PathVariable UUID postId) {
		ReadImageManagerUseCaseDto.Input input = ReadImageManagerUseCaseDto.Input.builder()
			.id(postId)
			.imageTypeList(List.of(DomainImageType.MAIN, DomainImageType.DETAIL))
			.build();

		ReadProductPostUseCaseDto.Output productPostOutPut = readProductPostUseCase.execute(postId);
		List<ImageManager> imageOutPut = readImageManagerUseCase.execute(input).imageManagerList();

		return ReadProductPostControllerDto.Response.builder()
			.id(productPostOutPut.productPostDto().getProductPost().getProductPostId())
			.rating(productPostOutPut.productPostDto().getProductPost().getRating())
			.productControllerDto(
				ReadProductPostControllerDto.ProductControllerDto.builder()
					.name(productPostOutPut.productPostDto().getProduct().getName())
					.price(productPostOutPut.productPostDto().getProduct().getPrice())
					.description(productPostOutPut.productPostDto().getProduct().getDescription())
					.mainImageList(convertToDtoByType(imageOutPut, DomainImageType.MAIN))
					.build()
			)
			.detailImageList(convertToDtoByType(imageOutPut, DomainImageType.DETAIL))
			.build();
	}

	// @GetMapping("/{postId}/reviews")
	// public Response readProductPostReviews(@PathVariable UUID postId) {
	//
	// }

	private List<ImageMangerControllerDto> convertToDtoByType(
		List<ImageManager> imageManagerList,
		DomainImageType domainImageType
	) {
		return imageManagerList.stream()
			.filter(image -> image.getDomainImageType() == domainImageType)
			.map(image -> ImageMangerControllerDto.builder()
				.url(image.getImage().imageUrl())
				.displayOrder(image.getDisplayOrder())
				.build())
			.toList();
	}

}
