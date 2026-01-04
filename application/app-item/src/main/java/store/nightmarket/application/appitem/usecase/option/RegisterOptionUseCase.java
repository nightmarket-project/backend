package store.nightmarket.application.appitem.usecase.option;

import static store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.SaveOptionPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;

@Service
@RequiredArgsConstructor
public class RegisterOptionUseCase implements BaseUseCase<Input, Void> {

	private final SaveOptionPort saveOptionPort;

	@Override
	public Void execute(Input input) {
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());

		saveOptionPort.save(
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
