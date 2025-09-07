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
import store.nightmarket.application.appitem.in.dto.ImageMangerResponseDto;
import store.nightmarket.application.appitem.in.dto.ReadProductPostResponseDto;
import store.nightmarket.application.appitem.in.dto.ReviewResponseDto;
import store.nightmarket.application.appitem.in.dto.UserResponseDto;
import store.nightmarket.application.appitem.usecase.ReadProductPostImageUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostUseCase;
import store.nightmarket.application.appitem.usecase.ReadReviewImageUseCase;
import store.nightmarket.application.appitem.usecase.ReadReviewUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostImageUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadReviewImageUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadReviewUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ReadProductPostControllerV1 {

	private final ReadProductPostUseCase readProductPostUseCase;
	private final ReadProductPostImageUseCase readProductPostImageUseCase;
	private final ReadReviewUseCase readReviewUseCase;
	private final ReadReviewImageUseCase readReviewImageUseCase;

	@GetMapping("/{postId}")
	public ReadProductPostResponseDto.Response readProductPost(@PathVariable UUID postId) {
		ReadProductPostImageUseCaseDto.Input input = ReadProductPostImageUseCaseDto.Input.builder()
			.id(postId)
			.imageTypeList(List.of(DomainImageType.MAIN, DomainImageType.DETAIL))
			.build();

		ReadProductPostUseCaseDto.Output productPostOutput = readProductPostUseCase.execute(postId);
		List<ImageManager> imageOutput = readProductPostImageUseCase.execute(input).imageManagerList();

		return ReadProductPostResponseDto.Response.builder()
			.id(productPostOutput.productPostAdapterDto().getProductPost().getProductPostId())
			.rating(productPostOutput.productPostAdapterDto().getProductPost().getRating())
			.productControllerDto(
				ReadProductPostResponseDto.ProductControllerDto.builder()
					.productId(productPostOutput.productPostAdapterDto().getProductPost().getProductId())
					.name(productPostOutput.productPostAdapterDto().getProduct().getName())
					.price(productPostOutput.productPostAdapterDto().getProduct().getPrice())
					.description(productPostOutput.productPostAdapterDto().getProduct().getDescription())
					.mainImageList(getDtoListByImageType(imageOutput, DomainImageType.MAIN))
					.build()
			)
			.detailImageList(getDtoListByImageType(imageOutput, DomainImageType.DETAIL))
			.build();
	}

	@GetMapping("/{postId}/reviews")
	public ReviewResponseDto.Response readProductPostReview(@PathVariable UUID postId) {
		ReadReviewUseCaseDto.Output reviewOutput = readReviewUseCase.execute(postId);
		List<UUID> idList = reviewOutput.reviewAdapterDtoList().stream()
			.map(reviewDto -> reviewDto.getReview().getReviewId().getId())
			.toList();
		ReadReviewImageUseCaseDto.Input input = ReadReviewImageUseCaseDto.Input.builder()
			.idList(idList)
			.build();

		Map<UUID, ImageManager> imageMap = readReviewImageUseCase.execute(input)
			.imageManagerList().stream()
			.collect(Collectors.toMap(
				imageManager -> imageManager.getImageOwnerId().getId(),
				image -> image)
			);

		return ReviewResponseDto.Response.builder()
			.reviewControllerDtoList(
				reviewOutput.reviewAdapterDtoList().stream()
					.map(dto -> ReviewResponseDto.ReviewControllerDto.builder()
						.userResponseDto(
							UserResponseDto.builder()
								.userId(dto.getUser().getUserId())
								.name(dto.getUser().getName())
								.build())
						.commentText(dto.getReview().getCommentText())
						.imageMangerResponseDto(
							ImageMangerResponseDto.builder()
								.url(imageMap.get(dto.getReview().getReviewId().getId()).getImage().imageUrl())
								.displayOrder(imageMap.get(dto.getReview().getReviewId().getId()).getDisplayOrder())
								.build()
						)
						.rating(dto.getReview().getRating())
						.replyControllerDto(
							ReviewResponseDto.ReplyControllerDto.builder()
								.userResponseDto(
									UserResponseDto.builder()
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

	private List<ImageMangerResponseDto> getDtoListByImageType(
		List<ImageManager> imageManagerList,
		DomainImageType domainImageType
	) {
		return imageManagerList.stream()
			.filter(image -> image.getDomainImageType() == domainImageType)
			.map(image -> ImageMangerResponseDto.builder()
				.url(image.getImage().imageUrl())
				.displayOrder(image.getDisplayOrder())
				.build())
			.toList();
	}

}
