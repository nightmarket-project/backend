package store.nightmarket.application.appitem.usecase.option;

import static store.nightmarket.application.appitem.usecase.option.dto.ModifyOptionGroupUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.SaveOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.service.ModifyOptionGroupDomainService;
import store.nightmarket.domain.item.service.dto.ModifyOptionGroupDomainServiceDto;

@Service
@RequiredArgsConstructor
public class ModifyOptionGroupUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final ReadOptionGroupPort readOptionGroupPort;
	private final SaveOptionGroupPort saveOptionGroupPort;
	private final ModifyOptionGroupDomainService modifyOptionGroupDomainService;

	@Override
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new OptionException("Not Owner For OptionGroup");
		}

		OptionGroup optionGroup = readOptionGroupPort.readOrThrow(input.optionGroupId());

		ModifyOptionGroupDomainServiceDto.Event event = modifyOptionGroupDomainService.execute(
			ModifyOptionGroupDomainServiceDto.Input.builder()
				.optionGroup(optionGroup)
				.name(input.name())
				.order(input.displayOrder())
				.build()
		);

		saveOptionGroupPort.save(event.optionGroup());
		return null;
	}

}
