package store.nightmarket.application.appitem.in;

import static store.nightmarket.application.appitem.in.dto.ReadProductPostDto.*;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.usecase.ReadProductPostUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class ReadProductPostControllerV1 {

	private final ReadProductPostUseCase readProductPostUseCase;

	@GetMapping("/{postId}")
	public Response readProductPost(@PathVariable UUID postId) {
		ReadProductPostUseCaseDto.Output output = readProductPostUseCase.execute(postId);

		return Response.builder()
			.productPostDto(output.productPostDto())
			.optionGroupDtoList(output.optionGroupDtoList())
			.productVariantDtoList(output.productVariantDtoList())
			.build();
	}

}
