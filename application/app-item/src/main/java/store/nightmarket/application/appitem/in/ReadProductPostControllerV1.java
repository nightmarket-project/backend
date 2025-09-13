package store.nightmarket.application.appitem.in;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ReadProductPostDto;
import store.nightmarket.application.appitem.in.dto.ReadReviewDto;
import store.nightmarket.application.appitem.usecase.ReadImageManagerListUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostImageUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostUseCase;
import store.nightmarket.application.appitem.usecase.ReadReviewUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadImageManagerListUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostImageUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadReviewUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;
import store.nightmarket.itemweb.valueobject.ImageOwnerId;
import store.nightmarket.itemweb.valueobject.ProductPostId;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ReadProductPostControllerV1 {

	private final ReadProductPostUseCase readProductPostUseCase;
	private final ReadProductPostImageUseCase readProductPostImageUseCase;
	private final ReadReviewUseCase readReviewUseCase;
	private final ReadImageManagerListUseCase readImageManagerListUseCase;

	@GetMapping("/{postId}")
	public ReadProductPostDto.Response readProductPost(@PathVariable UUID postId) {
		ReadProductPostImageUseCaseDto.Input input = ReadProductPostImageUseCaseDto.Input.builder()
			.id(new ProductPostId(postId))
			.imageTypeList(List.of(DomainImageType.MAIN, DomainImageType.DETAIL))
			.build();

		ReadProductPostUseCaseDto.Output productPostOutput = readProductPostUseCase.execute(new ProductPostId(postId));
		List<ImageManager> imageOutput = readProductPostImageUseCase.execute(input).imageManagerList();

		return ReadProductPostDto.Response.builder()
			.id(productPostOutput.productPostAdapterDto().getProductPost().getProductPostId())
			.rating(productPostOutput.productPostAdapterDto().getProductPost().getRating())
			.productInfo(
				ReadProductPostDto.ProductInfo.builder()
					.productId(productPostOutput.productPostAdapterDto().getProductPost().getProductId())
					.name(productPostOutput.productPostAdapterDto().getProduct().getName())
					.price(productPostOutput.productPostAdapterDto().getProduct().getPrice())
					.description(productPostOutput.productPostAdapterDto().getProduct().getDescription())
					.mainImageList(getDtoListByImageType(imageOutput, DomainImageType.MAIN))
					.build()
			)
			.detailImageInfoList(getDtoListByImageType(imageOutput, DomainImageType.DETAIL))
			.build();
	}

	@GetMapping("/{postId}/reviews")
	public ReadReviewDto.Response readProductPostReview(@PathVariable UUID postId) {
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
			.reviewInfoList(
				reviewOutput.reviewAdapterDtoList().stream()
					.map(dto -> ReadReviewDto.ReviewInfo.builder()
						.userInfo(
							ReadReviewDto.UserInfo.builder()
								.userId(dto.getUser().getUserId())
								.name(dto.getUser().getName())
								.build())
						.commentText(dto.getReview().getCommentText())
						.imageManagerInfo(
							ReadReviewDto.ImageManagerInfo.builder()
								.url(imageMap.get(dto.getReview().getReviewId().getId()).getImage().imageUrl())
								.displayOrder(imageMap.get(dto.getReview().getReviewId().getId()).getDisplayOrder())
								.build()
						)
						.rating(dto.getReview().getRating())
						.replyInfo(
							ReadReviewDto.ReplyInfo.builder()
								.userInfo(
									ReadReviewDto.UserInfo.builder()
										.userId(dto.getReplyAdapterDto().getUser().getUserId())
										.name(dto.getReplyAdapterDto().getUser().getName())
										.build()
								)
								.commentText(dto.getReplyAdapterDto().getReply().getCommentText())
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
		DomainImageType domainImageType
	) {
		return imageManagerList.stream()
			.filter(image -> image.getDomainImageType() == domainImageType)
			.map(image -> ReadProductPostDto.ImageManagerInfo.builder()
				.url(image.getImage().imageUrl())
				.displayOrder(image.getDisplayOrder())
				.build())
			.toList();
	}

}
