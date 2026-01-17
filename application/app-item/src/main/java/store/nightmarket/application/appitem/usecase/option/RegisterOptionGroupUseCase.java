package store.nightmarket.application.appitem.usecase.option;

import static store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionGroupUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;

@Service
@RequiredArgsConstructor
public class RegisterOptionGroupUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final SaveOptionGroupPort saveOptionGroupPort;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new OptionException("Not Owner For Product");
		}

		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());

		saveOptionGroupPort.save(
			createOptionGroup(input, optionGroupId),
			createOptionValueList(input, optionGroupId)
		);

		return null;
	}

	private OptionGroup createOptionGroup(Input input, OptionGroupId optionGroupId) {
		return OptionGroup.newInstance(
			optionGroupId,
			input.productId(),
			input.name(),
			input.displayOrder()
		);
	}

	private List<OptionValue> createOptionValueList(Input input, OptionGroupId optionGroupId) {
		return input.optionValueDtoList().stream()
			.map(dto ->
				OptionValue.newInstance(
					new OptionValueId(UUID.randomUUID()),
					optionGroupId,
					dto.name(),
					dto.price(),
					dto.displayOrder()
				)
			)
			.toList();
	}

}
