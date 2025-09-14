package store.nightmarket.application.appitem.in;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.SearchProductDto;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.application.appitem.usecase.FindProductByComponentUseCase;
import store.nightmarket.application.appitem.usecase.dto.FindProductByComponentUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ProductPostControllerV1 {

	private final FindProductByComponentUseCase findProductByComponentUseCase;

	@GetMapping("/{component}")
	public SearchProductDto.Response searchProduct(
		@PathVariable("component") String component,
		@RequestParam(defaultValue = "0") int page
	) {
		// component 값이 null 이거나 비어 있을때 어떻게 처리할까 고민 중
		if (page < 0)
			page = 0;

		FindProductByComponentUseCaseDto.Output output = findProductByComponentUseCase.execute(
			FindProductByComponentUseCaseDto.Input.builder()
				.component(component)
				.page(page)
				.build()
		);
		Page<ProductPostAdapterDto> productPostPage = output.dtoPage();
		List<ImageManager> imageManagerList = output.imageManagerList();
		Map<ImageOwnerId, Image> imageMap = imageManagerList.stream()
			.collect(Collectors.toMap(ImageManager::getImageOwnerId, ImageManager::getImage));

		return SearchProductDto.Response.builder()
			.content(
				productPostPage.getContent().stream()
					.map(productPostAdapterDto ->
						SearchProductDto.ProductInfo.builder()
							.productPostId(productPostAdapterDto.getProductPost().getProductPostId().getId())
							.image(imageMap.get(productPostAdapterDto.getProductPost().getProductPostId()))
							.price(productPostAdapterDto.getProduct().getPrice())
							.name(productPostAdapterDto.getProduct().getName())
							.rating(productPostAdapterDto.getProductPost().getRating())
							.build())
					.toList())
			.currentPage(page)
			.numberOfElements(productPostPage.getNumberOfElements())
			.totalPage(productPostPage.getTotalPages())
			.totalElements(productPostPage.getTotalElements())
			.hasNext(productPostPage.hasNext())
			.build();
	}

}
