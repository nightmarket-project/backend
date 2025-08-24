package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;

@Builder
public record ProductControllerDto(
	String name,
	BigDecimal price,
	List<ImageMangerControllerDto> imageMangerControllerDtoList,
	String description
) {

}
