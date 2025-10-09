package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ValidateProductStockUseCaseDto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.itemweb.exception.ItemWebException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateProductStockUseCase implements BaseUseCase<Input, Void> {

	private final ProductStockValidator productStockValidator;

	@Override
	@Transactional
	public Void execute(Input input) {

		String threadName = Thread.currentThread().getName();
		log.info("[{}] 🚀 UseCase 실행 시작", threadName); // 실행 시작 로깅

		List<ProductQuantityDto> reservedProduct = new ArrayList<>();
		List<String> validationErrors = input.checkProductList().stream()
			.map(checkProduct -> {
				try {
					productStockValidator.validateAndReserveStock(
						checkProduct.productVariantId(),
						checkProduct.quantity(),
						input.userId()
					);
				} catch (ItemWebException e) {
					return e.getMessage();
				}
				reservedProduct.add(checkProduct);
				return null;
			})
			.filter(Objects::nonNull)
			.toList();

		if (!validationErrors.isEmpty()) {
			reservedProduct.forEach(
				productQuantityDto -> productStockValidator.rollbackReservation(
					productQuantityDto.productVariantId(),
					productQuantityDto.quantity(),
					input.userId()));
			throw new ItemWebException(String.join("\n", validationErrors));
		}

		return null;
	}

}
