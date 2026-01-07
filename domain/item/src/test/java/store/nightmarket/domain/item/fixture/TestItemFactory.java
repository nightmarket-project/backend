package store.nightmarket.domain.item.fixture;

import java.math.BigInteger;
import java.util.UUID;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;

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
			new Quantity(BigInteger.valueOf(quantity))
		);
	}

	public static OptionGroup createOptionGroup(
		UUID optionGroupId,
		UUID productId,
		String name,
		int order
	) {
		return OptionGroup.newInstance(
			new OptionGroupId(optionGroupId),
			new ProductId(productId),
			new Name(name),
			order
		);
	}

	public static OptionValue createOptionValue(
		UUID optionValueId,
		UUID optionGroupId,
		String name,
		int price,
		int order
	) {
		return OptionValue.newInstance(
			new OptionValueId(optionValueId),
			new OptionGroupId(optionGroupId),
			new Name(name),
			new Price(BigInteger.valueOf(price)),
			order
		);
	}

}
