package store.nightmarket.application.appitem.usecase.variant;

import static store.nightmarket.application.appitem.usecase.variant.dto.DeleteProductVariantUseCaseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.exception.ProductVariantException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.ProductVariant;

@Service
@RequiredArgsConstructor
public class DeleteProductVariantUseCase implements BaseUseCase<Input, Void> {

	private final ReadProductVariantPort readProductVariantPort;
	private final ReadProductPort readProductPort;
	private final DeleteProductVariantPort deleteProductVariantPort;
	private final DeleteVariantOptionValuePort deleteVariantOptionValuePort;

	@Override
	@Transactional
	public Void execute(Input input) {
		ProductVariant productVariant = readProductVariantPort.readOrThrow(input.productVariantId());
		Product product = readProductPort.readOrThrow(input.productId());

		if (!product.isOwner(input.userId())) {
			throw new ProductVariantException("Not Owner For ProductVariant");
		}

		deleteProductVariantPort.delete(input.productVariantId());
		deleteVariantOptionValuePort.deleteByProductVariantId(input.productVariantId());
		return null;
	}

}
