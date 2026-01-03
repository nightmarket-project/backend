package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.DeleteOptionGroupUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.application.appitem.out.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadVariantOptionValuePort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Service
@RequiredArgsConstructor
public class DeleteOptionGroupUseCase implements BaseUseCase<Input, Void> {

	private final ReadOptionGroupPort readOptionGroupPort;
	private final ReadProductPort readProductPort;
	private final ReadVariantOptionValuePort readVariantOptionValuePort;
	private final DeleteOptionGroupPort deleteOptionGroupPort;
	private final DeleteVariantOptionValuePort deleteVariantOptionValuePort;
	private final DeleteProductVariantPort deleteProductVariantPort;
	private final DeleteOptionValuePort deleteOptionValuePort;

	@Override
	@Transactional
	public Void execute(Input input) {
		OptionGroup optionGroup = readOptionGroupPort.readOrThrow(input.optionGroupId());
		Product product = readProductPort.readOrThrow(optionGroup.getProductId());

		if (!product.isOwner(input.userId())) {
			throw new OptionException("Not Owner For OptionGroup");
		}

		List<ProductVariantId> productVariantIdList
			= readVariantOptionValuePort.readProductVariantIdsByOptionGroupId(optionGroup.getOptionGroupId());

		deleteVariantOptionValuePort.deleteByOptionGroupId(optionGroup.getOptionGroupId());
		deleteOptionValuePort.deleteByOptionGroupId(optionGroup.getOptionGroupId());
		deleteOptionGroupPort.deleteById(input.optionGroupId());
		//deleteProductVariantPort.deleteAll(productVariantIdList);
		/// TO-DO: 추후 도메인 상의 후 반영
		return null;
	}

}
