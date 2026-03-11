package store.nightmarket.application.appitem.in.dto;

import java.util.UUID;

import lombok.Builder;

public class RegisterPostDto {

	@Builder
	public record Request(
		UUID productId
	) {

	}

}
