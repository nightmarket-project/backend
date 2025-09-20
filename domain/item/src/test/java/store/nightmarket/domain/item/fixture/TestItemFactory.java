package store.nightmarket.domain.item.fixture;

import java.math.BigDecimal;
import java.util.UUID;

import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

public class TestItemFactory {

	public static ProductVariant createProductVariant(
		UUID productVariantId,
		UUID productId,
		UUID sellerId,
		String SKUId,
		int quantity
	) {
		return ProductVariant.newInstance(
			new ProductVariantId(productVariantId),
			new ProductId(productId),
			new UserId(sellerId),
			SKUId,
			new Quantity(BigDecimal.valueOf(quantity))
		);
	}

}
