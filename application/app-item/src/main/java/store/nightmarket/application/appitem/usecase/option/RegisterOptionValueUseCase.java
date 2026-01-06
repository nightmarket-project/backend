package store.nightmarket.application.appitem.usecase.option;

import static store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionValueUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.option.SaveOptionValuePort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionValueId;

@Service
@RequiredArgsConstructor
public class RegisterOptionValueUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final ReadOptionGroupPort readOptionGroupPort;
	private final SaveOptionValuePort saveOptionValuePort;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new OptionException("Not Owner For Product");
		}

		OptionGroup optionGroup = readOptionGroupPort.readOrThrow(input.optionGroupId());

		saveOptionValuePort.save(
			createOptionValue(input),
			optionGroup
		);

		return null;
	}

	private OptionValue createOptionValue(Input input) {
		return OptionValue.newInstance(
			new OptionValueId(UUID.randomUUID()),
			input.optionGroupId(),
			input.name(),
			input.price(),
			input.displayOrder()
		);
	}

}
