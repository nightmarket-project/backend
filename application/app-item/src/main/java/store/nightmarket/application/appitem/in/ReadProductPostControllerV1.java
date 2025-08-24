package store.nightmarket.application.appitem.in;

import static store.nightmarket.application.appitem.in.dto.ReadProductPostDto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ImageMangerControllerDto;
import store.nightmarket.application.appitem.in.dto.OptionGroupControllerDto;
import store.nightmarket.application.appitem.in.dto.OptionGroupValueControllerDto;
import store.nightmarket.application.appitem.in.dto.OptionLayerDto;
import store.nightmarket.application.appitem.in.dto.OptionValueControllerDto;
import store.nightmarket.application.appitem.in.dto.ProductControllerDto;
import store.nightmarket.application.appitem.in.dto.ProductPostControllerDto;
import store.nightmarket.application.appitem.in.dto.ReplyControllerDto;
import store.nightmarket.application.appitem.in.dto.ReviewControllerDto;
import store.nightmarket.application.appitem.in.dto.ReviewReplyControllerDto;
import store.nightmarket.application.appitem.out.dto.OptionGroupDto;
import store.nightmarket.application.appitem.out.dto.ReviewDto;
import store.nightmarket.application.appitem.usecase.ReadImageManagerUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductPostUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.ProductPost;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ReadProductPostControllerV1 {

	private final ReadProductPostUseCase readProductPostUseCase;
	private final ReadImageManagerUseCase readImageManagerUseCase;
	private final OptionLayerFactory optionLayerFactory;

	@GetMapping("/{postId}")
	public Response readProductPost(@PathVariable UUID postId) {
		ReadProductPostUseCaseDto.Output output = readProductPostUseCase.execute(postId);

		List<ImageManager> outputImageManagerList = readImageManagerUseCase.execute(
			output.productPostDto().getProductPost().getProductId().getId());

		List<ImageMangerControllerDto> prouductPostImageDtoList = new ArrayList<>();
		for (ImageManager imageManager : outputImageManagerList) {
			ImageMangerControllerDto imageMangerControllerDto = ImageMangerControllerDto.builder()
				.url(imageManager.getImage().imageUrl())
				.displayOrder(imageManager.getDisplayOrder())
				.build();
			prouductPostImageDtoList.add(imageMangerControllerDto);
		}

		Product product = output.productPostDto().getProduct();
		ProductControllerDto productPostControllerDto = ProductControllerDto.builder()
			.name(product.getName().getValue())
			.price(product.getPrice().amount())
			.imageMangerControllerDtoList(prouductPostImageDtoList)
			.description(product.getDescription())
			.build();

		ProductPost productPost = output.productPostDto().getProductPost();
		ProductPostControllerDto postControllerDto = ProductPostControllerDto.builder()
			.productPostId(productPost.getProductId().getId())
			.productController(productPostControllerDto)
			.rating(productPost.getRating())
			.build();

		List<ReviewReplyControllerDto> reviewReplyControllerDtoList = new ArrayList<>();
		for (ReviewDto reviewDto : output.productPostDto().getReviewDtoList()) {
			List<ImageManager> reviewImageManager = readImageManagerUseCase.execute(
				reviewDto.getReview().getReviewId().getId());
			ImageMangerControllerDto reviewImageControllerDto = ImageMangerControllerDto.builder()
				.url(reviewImageManager.getFirst().getImage().imageUrl())
				.displayOrder(reviewImageManager.getFirst().getDisplayOrder())
				.build();
			ReviewControllerDto reviewControllerDto = ReviewControllerDto.builder()
				// .user()
				.commentText(reviewDto.getReview().getCommentText().getValue())
				.image(reviewImageControllerDto)
				.rating(reviewDto.getReview().getRating().value())
				.build();
			ReplyControllerDto replyControllerDto = ReplyControllerDto.builder()
				// .user()
				.comment(reviewDto.getReply().getCommentText().getValue())
				.build();
			ReviewReplyControllerDto reviewReplyControllerDto = ReviewReplyControllerDto.builder()
				.reviewControllerDto(reviewControllerDto)
				.replyControllerDto(replyControllerDto)
				.build();
			reviewReplyControllerDtoList.add(reviewReplyControllerDto);
		}

		List<OptionGroupValueControllerDto> optionGroupValueControllerDtoList = new ArrayList<>();
		for (OptionGroupDto optionGroupDto : output.optionGroupDtoList()) {
			OptionGroupControllerDto optionGroupControllerDto = OptionGroupControllerDto.builder()
				.optionGroupId(optionGroupDto.getOptionGroup().getOptionGroupId().getId())
				.name(optionGroupDto.getOptionGroup().getName().getValue())
				.displayOrder(optionGroupDto.getOptionGroup().getOrder())
				.build();
			List<OptionValueControllerDto> optionValueControllerDtoList = new ArrayList<>();
			for (OptionValue optionValue : optionGroupDto.getOptionValueList()) {
				OptionValueControllerDto optionValueControllerDto = OptionValueControllerDto.builder()
					.OptionGroupId(optionValue.getOptionGroupId().getId())
					.value(optionValue.getValue())
					.price(optionValue.getPrice().amount())
					.displayOrder(optionValue.getOrder())
					.build();
				optionValueControllerDtoList.add(optionValueControllerDto);
			}
			OptionGroupValueControllerDto optionGroupValueControllerDto = OptionGroupValueControllerDto.builder()
				.optionGroupControllerDto(optionGroupControllerDto)
				.optionValueControllerDtoList(optionValueControllerDtoList)
				.build();
			optionGroupValueControllerDtoList.add(optionGroupValueControllerDto);
		}

		List<OptionLayerDto> optionLayerDtoList = optionLayerFactory.create(
			output.optionGroupDtoList(),
			output.productVariantDtoList()
		);

		return Response.builder()
			.productPostControllerDto(postControllerDto)
			.reviewReplyControllerDtoList(reviewReplyControllerDtoList)
			.optionGroupValueControllerDtoList(optionGroupValueControllerDtoList)
			.optionLayerDtoList(optionLayerDtoList)
			.build();
	}

}
