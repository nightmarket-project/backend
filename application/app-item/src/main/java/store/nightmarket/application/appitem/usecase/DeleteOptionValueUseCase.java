package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.DeleteOptionValueUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.ReadOptionValuePort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.OptionValue;

@Service
@RequiredArgsConstructor
public class DeleteOptionValueUseCase implements BaseUseCase<Input, Void> {

	private final ReadOptionValuePort readOptionValuePort;
	private final DeleteOptionValuePort deleteOptionValuePort;

	@Override
	public Void execute(Input input) {
		OptionValue optionValue = readOptionValuePort.readOrThrow(input.optionValueId());

		deleteOptionValuePort.delete(optionValue.getOptionValueId());

		return null;
	}

}
