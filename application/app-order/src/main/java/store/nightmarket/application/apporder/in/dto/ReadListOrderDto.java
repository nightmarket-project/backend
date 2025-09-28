package store.nightmarket.application.apporder.in.dto;

import java.util.List;

import lombok.Builder;

public class ReadListOrderDto {

	@Builder
	public record Response(
		List<ReadOrderDto.Response> contents,
		int currentPage,
		int numberOfElements,
		int totalPage,
		long totalElements,
		boolean hasNext
	) {

	}

}
