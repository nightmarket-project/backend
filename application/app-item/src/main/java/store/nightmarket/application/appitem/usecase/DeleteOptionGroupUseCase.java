package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.DeleteOptionGroupUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;

@Service
@RequiredArgsConstructor
public class DeleteOptionGroupUseCase implements BaseUseCase<Input, Void> {

	private final ReadOptionGroupPort readOptionGroupPort;
	private final DeleteOptionGroupPort deleteOptionGroupPort;

	@Override
	public Void execute(Input input) {
		OptionGroup optionGroup = readOptionGroupPort.readOrThrow(input.optionGroupId());

		if (!optionGroup.isOwner(input.userId())) {
			throw new OptionException("Not Owner For OptionGroup");
		}

		deleteOptionGroupPort.delete(optionGroup);
		return null;
	}

}
