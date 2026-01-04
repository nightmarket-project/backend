package store.nightmarket.application.appitem.usecase.option;

import static store.nightmarket.application.appitem.usecase.option.dto.DeleteOptionValueUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.option.ReadOptionValuePort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.application.appitem.out.variant.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.variant.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.variant.ReadVariantOptionValuePort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Service
@RequiredArgsConstructor
public class DeleteOptionValueUseCase implements BaseUseCase<Input, Void> {

	private final ReadOptionValuePort readOptionValuePort;
	private final ReadOptionGroupPort readOptionGroupPort;
	private final ReadProductPort readProductPort;
	private final ReadVariantOptionValuePort readVariantOptionValuePort;
	private final DeleteOptionValuePort deleteOptionValuePort;
	private final DeleteVariantOptionValuePort deleteVariantOptionValuePort;
	private final DeleteProductVariantPort deleteProductVariantPort;

	@Override
	@Transactional
	public Void execute(Input input) {
		OptionValue optionValue = readOptionValuePort.readOrThrow(input.optionValueId());
		OptionGroup optionGroup = readOptionGroupPort.readOrThrow(input.optionGroupId());
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new OptionException("Not Owner For OptionGroup");
		}

		List<ProductVariantId> productVariantIdList
			= readVariantOptionValuePort.readProductVariantIdsByOptionValueId(input.optionValueId());

		deleteVariantOptionValuePort.deleteByOptionValueId(input.optionValueId());
		deleteOptionValuePort.delete(input.optionValueId());
		//deleteProductVariantPort.deleteAll(productVariantIdList);
		/// To-DO: 추후 도메인 상의 후 반영
		return null;
	}

}
