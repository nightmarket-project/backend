package store.nightmarket.application.appitem.in;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ReadProductPostDto;
import store.nightmarket.application.appitem.in.dto.ReadReviewDto;
import store.nightmarket.application.appitem.in.dto.SearchProductDto;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.application.appitem.usecase.FindProductByKeywordUseCase;
import store.nightmarket.application.appitem.usecase.ReadImageManagerListUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostImageUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostUseCase;
import store.nightmarket.application.appitem.usecase.ReadReviewUseCase;
import store.nightmarket.application.appitem.usecase.dto.FindProductByKeywordUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadImageManagerListUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostImageUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadReviewUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.state.ImageType;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.model.id.ImageOwnerId;
import store.nightmarket.itemweb.model.id.ProductPostId;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ProductPostControllerV1 {

	private final FindProductByKeywordUseCase findProductByKeywordUseCase;
	private final ReadProductPostUseCase readProductPostUseCase;
	private final ReadProductPostImageUseCase readProductPostImageUseCase;
	private final ReadReviewUseCase readReviewUseCase;
	private final ReadImageManagerListUseCase readImageManagerListUseCase;

	@GetMapping("/search")
	public SearchProductDto.Response searchProduct(
		@RequestParam("keyword") String keyword,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "25") int size
	) {
		// component 값이 null 이거나 비어 있을때 어떻게 처리할까 고민 중
		if (page < 0)
			page = 0;

		FindProductByKeywordUseCaseDto.Output output = findProductByKeywordUseCase.execute(
			FindProductByKeywordUseCaseDto.Input.builder()
				.keyword(keyword)
				.page(page)
				.size(size)
				.build()
		);
		Page<ProductPostAdapterDto> productPostPage = output.dtoPage();
		List<ImageManager> imageManagerList = output.imageManagerList();
		Map<UUID, Image> imageMap = imageManagerList.stream()
			.collect(Collectors.toMap(imageManager -> imageManager.getImageOwnerId().getId(), ImageManager::getImage));

		return SearchProductDto.Response.builder()
			.contents(
				productPostPage.getContent().stream()
					.map(productPostAdapterDto ->
						SearchProductDto.ProductInfo.builder()
							.productPostId(productPostAdapterDto.getProductPost().getProductPostId().getId())
							.imageUrl(imageMap.get(productPostAdapterDto.getProductPost().getProductPostId().getId())
								.imageUrl())
							.price(productPostAdapterDto.getProduct().getPrice().amount())
							.name(productPostAdapterDto.getProduct().getName().getValue())
							.rating(productPostAdapterDto.getProductPost().getRating().value())
							.build())
					.toList())
			.currentPage(page)
			.numberOfElements(productPostPage.getNumberOfElements())
			.totalPage(productPostPage.getTotalPages())
			.totalElements(productPostPage.getTotalElements())
			.hasNext(productPostPage.hasNext())
			.build();
	}

	@GetMapping("/{postId}")
	public ReadProductPostDto.Response readProductPost(@PathVariable("postId") UUID postId) {
		ReadProductPostImageUseCaseDto.Input input = ReadProductPostImageUseCaseDto.Input.builder()
			.id(new ProductPostId(postId))
			.imageTypeList(List.of(ImageType.MAIN, ImageType.DETAIL))
			.build();

		ReadProductPostUseCaseDto.Output productPostOutput = readProductPostUseCase.execute(new ProductPostId(postId));
		List<ImageManager> imageOutput = readProductPostImageUseCase.execute(input).imageManagerList();

		return ReadProductPostDto.Response.builder()
			.id(productPostOutput.productPostAdapterDto().getProductPost().getProductPostId().getId())
			.rating(productPostOutput.productPostAdapterDto().getProductPost().getRating().value())
			.productInfo(
				ReadProductPostDto.ProductInfo.builder()
					.productId(productPostOutput.productPostAdapterDto().getProductPost().getProductId().getId())
					.name(productPostOutput.productPostAdapterDto().getProduct().getName().getValue())
					.price(productPostOutput.productPostAdapterDto().getProduct().getPrice().amount())
					.description(productPostOutput.productPostAdapterDto().getProduct().getDescription())
					.build()
			)
			.mainImageList(getDtoListByImageType(imageOutput, ImageType.MAIN))
			.detailImageInfoList(getDtoListByImageType(imageOutput, ImageType.DETAIL))
			.build();
	}

	@GetMapping("/{postId}/reviews")
	public ReadReviewDto.Response readProductPostReview(@PathVariable("postId") UUID postId) {
		ReadReviewUseCaseDto.Output reviewOutput = readReviewUseCase.execute(new ProductPostId(postId));
		List<UUID> idList = reviewOutput.reviewAdapterDtoList().stream()
			.map(reviewDto -> reviewDto.getReview().getReviewId().getId())
			.toList();
		ReadImageManagerListUseCaseDto.Input input = ReadImageManagerListUseCaseDto.Input.builder()
			.idList(idList.stream().map(ImageOwnerId::new).toList())
			.build();

		Map<UUID, ImageManager> imageMap = readImageManagerListUseCase.execute(input)
			.imageManagerList().stream()
			.collect(Collectors.toMap(
				imageManager -> imageManager.getImageOwnerId().getId(),
				image -> image)
			);

		return ReadReviewDto.Response.builder()
			.reviewList(
				reviewOutput.reviewAdapterDtoList().stream()
					.map(dto -> ReadReviewDto.ReviewInfo.builder()
						.user(
							ReadReviewDto.UserInfo.builder()
								.userId(dto.getUser().getUserId().getId())
								.name(dto.getUser().getName().getValue())
								.build())
						.comment(dto.getReview().getCommentText().getValue())
						.imageUrl(imageMap.get(dto.getReview().getReviewId().getId()).getImage().imageUrl())
						.rating(dto.getReview().getRating().value())
						.replyInfo(
							ReadReviewDto.ReplyInfo.builder()
								.user(
									ReadReviewDto.UserInfo.builder()
										.userId(dto.getReplyAdapterDto().getUser().getUserId().getId())
										.name(dto.getReplyAdapterDto().getUser().getName().getValue())
										.build()
								)
								.comment(dto.getReplyAdapterDto().getReply().getCommentText().getValue())
								.build()
						)
						.build()
					)
					.toList()
			)
			.build();
	}

	private List<ReadProductPostDto.ImageManagerInfo> getDtoListByImageType(
		List<ImageManager> imageManagerList,
		ImageType imageType
	) {
		return imageManagerList.stream()
			.filter(image -> image.getImageType() == imageType)
			.map(image -> ReadProductPostDto.ImageManagerInfo.builder()
				.imageUrl(image.getImage().imageUrl())
				.displayOrder(image.getDisplayOrder())
				.build())
			.toList();

	}

}
