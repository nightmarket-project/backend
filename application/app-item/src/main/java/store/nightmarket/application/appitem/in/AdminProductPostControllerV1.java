package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.RegisterAndScheduleProductPostDto;
import store.nightmarket.application.appitem.usecase.post.RegisterAndScheduleProductPostUseCase;
import store.nightmarket.application.appitem.usecase.post.dto.RegisterAndScheduleProductPostUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;

@RestController
@RequestMapping("api/v1/admin/posts")
@RequiredArgsConstructor
public class AdminProductPostControllerV1 {

	private final RegisterAndScheduleProductPostUseCase registerAndScheduleProductPostUseCase;

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerAndScheduleProductPost(
		@RequestBody RegisterAndScheduleProductPostDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		registerAndScheduleProductPostUseCase.execute(
			RegisterAndScheduleProductPostUseCaseDto.Input.builder()
				.productId(new ProductId(request.productId()))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.publishAt(request.publishAt())
				.expiredAt(request.expiredAt())
				.build()
		);
	}

}
