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
import store.nightmarket.application.appitem.in.dto.ImageMangerControllerDto;
import store.nightmarket.application.appitem.in.dto.ReadProductPostControllerDto;
import store.nightmarket.application.appitem.in.dto.ReviewListControllerDto;
import store.nightmarket.application.appitem.in.dto.UserControllerDto;
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
	public ReadProductPostControllerDto.Response readProductPost(@PathVariable UUID postId) {
		ReadProductPostImageUseCaseDto.Input input = ReadProductPostImageUseCaseDto.Input.builder()
			.id(postId)
			.imageTypeList(List.of(DomainImageType.MAIN, DomainImageType.DETAIL))
			.build();

		ReadProductPostUseCaseDto.Output productPostOutput = readProductPostUseCase.execute(postId);
		List<ImageManager> imageOutput = readProductPostImageUseCase.execute(input).imageManagerList();

		return ReadProductPostControllerDto.Response.builder()
			.id(productPostOutput.productPostDto().getProductPost().getProductPostId())
			.rating(productPostOutput.productPostDto().getProductPost().getRating())
			.productControllerDto(
				ReadProductPostControllerDto.ProductControllerDto.builder()
					.name(productPostOutput.productPostDto().getProduct().getName())
					.price(productPostOutput.productPostDto().getProduct().getPrice())
					.description(productPostOutput.productPostDto().getProduct().getDescription())
					.mainImageList(convertToDtoByType(imageOutput, DomainImageType.MAIN))
					.build()
			)
			.detailImageList(convertToDtoByType(imageOutput, DomainImageType.DETAIL))
			.build();
	}

	@GetMapping("/{postId}/reviews")
	public ReviewListControllerDto.Response readProductPostReviews(@PathVariable UUID postId) {
		ReadReviewUseCaseDto.Output reviewOutput = readReviewUseCase.execute(postId);
		List<UUID> idList = reviewOutput.reviewDtoList().stream()
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

		return ReviewListControllerDto.Response.builder()
			.reviewControllerDtoList(
				reviewOutput.reviewDtoList().stream()
					.map(dto -> ReviewListControllerDto.ReviewControllerDto.builder()
						.userControllerDto(
							UserControllerDto.builder()
								.userId(dto.getUser().getUserId())
								.name(dto.getUser().getName())
								.build())
						.commentText(dto.getReview().getCommentText())
						.imageMangerControllerDto(
							ImageMangerControllerDto.builder()
								.url(imageMap.get(dto.getReview().getReviewId().getId()).getImage().imageUrl())
								.displayOrder(imageMap.get(dto.getReview().getReviewId().getId()).getDisplayOrder())
								.build()
						)
						.rating(dto.getReview().getRating())
						.replyControllerDto(
							ReviewListControllerDto.ReplyControllerDto.builder()
								.userControllerDto(
									UserControllerDto.builder()
										.userId(dto.getReplyDto().getUser().getUserId())
										.name(dto.getReplyDto().getUser().getName())
										.build()
								)
								.commentText(dto.getReplyDto().getReply().getCommentText())
								.build()
						)
						.build()
					)
					.toList()
			)
			.build();
	}

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
