package store.nightmarket.application.appitem.in.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;

public class SchedulePostDto {

	@Builder
	public record Request(
		LocalDateTime scheduleAt,
		String type,
		JsonNode payload
	) {

	}

}
