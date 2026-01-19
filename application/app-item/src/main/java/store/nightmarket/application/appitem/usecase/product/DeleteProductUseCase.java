package store.nightmarket.application.appitem.usecase.product;

import static store.nightmarket.application.appitem.usecase.product.dto.DeleteProductUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.application.appitem.out.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.DeleteProductPort;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadVariantOptionValuePort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductPort readProductPort;
	private final ReadVariantOptionValuePort readVariantOptionValuePort;
	private final ReadOptionGroupPort readOptionGroupPort;
	private final DeleteProductVariantPort deleteProductVariantPort;
	private final DeleteVariantOptionValuePort deleteVariantOptionValuePort;
	private final DeleteOptionValuePort deleteOptionValuePort;
	private final DeleteOptionGroupPort deleteOptionGroupPort;
	private final DeleteProductPort deleteProductPort;

	@Override
	@Transactional
	public Void execute(Input input) {
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new ProductException("Not Owner For Product");
		}

		List<ProductVariantId> productVariantIdList
			= readVariantOptionValuePort.readProductVariantIdsByProductId(input.productId());

		List<OptionGroupId> optionGroupIdList
			= readOptionGroupPort.readOptionGroupIdsByProductId(input.productId());

		deleteProductVariantPort.deleteAll(productVariantIdList);
		deleteVariantOptionValuePort.deleteAllByProductVariantIdList(productVariantIdList);
		deleteOptionValuePort.deleteAllByOptionGroupId(optionGroupIdList);
		deleteOptionGroupPort.deleteAll(optionGroupIdList);
		deleteProductPort.delete(input.productId());
		return null;
	}

}
