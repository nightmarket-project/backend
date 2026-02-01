package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.RegisterPostDto;
import store.nightmarket.application.appitem.in.dto.SchedulePostDto;
import store.nightmarket.application.appitem.usecase.post.CancelScheduleUseCase;
import store.nightmarket.application.appitem.usecase.post.DeleteProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.PublishProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.RegisterProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.ScheduleProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.UnpublishProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.ValidateOwnerUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.CancelScheduleUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.DeleteProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.PublishProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.RegisterProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.ScheduleProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.UnpublishProductPostUseCaseDto;
import store.nightmarket.application.appitem.usecase.post.dto.ValidateOwnerUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.id.SchedulePostId;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@RestController
@RequestMapping("api/v1/admin/posts")
@RequiredArgsConstructor
public class AdminProductPostControllerV1 {

	private final RegisterProductPostUseCase registerProductPostUseCase;
	private final ValidateOwnerUseCase validateOwnerUseCase;
	private final PublishProductPostUseCase publishProductPostUseCase;
	private final UnpublishProductPostUseCase unpublishProductPostUseCase;
	private final DeleteProductPostUseCase deleteProductPostUseCase;
	private final ScheduleProductPostUseCase scheduleProductPostUseCase;
	private final CancelScheduleUseCase cancelScheduleUseCase;

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerPost(
		@RequestBody RegisterPostDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		registerProductPostUseCase.execute(
			RegisterProductPostUseCaseDto.Input.builder()
				.productId(new ProductId(request.productId()))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

	@PostMapping("/{postId}/publish")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void publishPost(
		@PathVariable("postId") UUID productPostId,
		@AuthorizedUser UserSession userSession
	) {
		validateOwnerUseCase.execute(
			ValidateOwnerUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);

		publishProductPostUseCase.execute(
			PublishProductPostUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.build()
		);
	}

	@PostMapping("/{postId}/unpublish")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void unpublishPost(
		@PathVariable("postId") UUID productPostId,
		@AuthorizedUser UserSession userSession
	) {
		validateOwnerUseCase.execute(
			ValidateOwnerUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);

		unpublishProductPostUseCase.execute(
			UnpublishProductPostUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.build()
		);
	}

	@PostMapping("/{postId}/delete")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void deletePost(
		@PathVariable("postId") UUID productPostId,
		@AuthorizedUser UserSession userSession
	) {
		validateOwnerUseCase.execute(
			ValidateOwnerUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);

		deleteProductPostUseCase.execute(
			DeleteProductPostUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.build()
		);
	}

	@PostMapping("/{postId}/schedule")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void schedulePost(
		@PathVariable("postId") UUID productPostId,
		@RequestBody SchedulePostDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		validateOwnerUseCase.execute(
			ValidateOwnerUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);

		scheduleProductPostUseCase.execute(
			ScheduleProductPostUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.scheduledAt(request.scheduleAt())
				.type(ScheduleActionType.valueOf(request.type()))
				.build()
		);
	}

	@PostMapping("/{postId}/schedule/{scheduleId}/cancel")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void cancelSchedule(
		@PathVariable("postId") UUID productPostId,
		@PathVariable("scheduleId") UUID schedulePostId,
		@AuthorizedUser UserSession userSession
	) {
		validateOwnerUseCase.execute(
			ValidateOwnerUseCaseDto.Input.builder()
				.productPostId(new ProductPostId(productPostId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);

		cancelScheduleUseCase.execute(
			CancelScheduleUseCaseDto.Input.builder()
				.schedulePostId(new SchedulePostId(schedulePostId))
				.build()
		);
	}

}
