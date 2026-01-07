package store.nightmarket.application.appitem.usecase.option;

import static store.nightmarket.application.appitem.usecase.option.dto.ModifyOptionValueUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.option.ReadOptionValuePort;
import store.nightmarket.application.appitem.out.option.SaveOptionValuePort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.service.ModifyOptionValueDomainService;
import store.nightmarket.domain.item.service.dto.ModifyOptionValueDomainServiceDto;

@Service
@RequiredArgsConstructor
public class ModifyOptionValueUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final ReadOptionGroupPort readOptionGroupPort;
	private final ReadOptionValuePort readOptionValuePort;
	private final SaveOptionValuePort saveOptionValuePort;
	private final ModifyOptionValueDomainService modifyOptionValueDomainService;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new OptionException("Not Owner For OptionValue");
		}

		OptionGroup optionGroup = readOptionGroupPort.readOrThrow(input.optionGroupId());
		OptionValue optionValue = readOptionValuePort.readOrThrow(input.optionValueId());

		ModifyOptionValueDomainServiceDto.Event event = modifyOptionValueDomainService.execute(
			ModifyOptionValueDomainServiceDto.Input.builder()
				.optionValue(optionValue)
				.name(input.name())
				.price(input.price())
				.order(input.displayOrder())
				.build()
		);

		saveOptionValuePort.save(event.optionValue(), optionGroup);
		return null;
	}

}
