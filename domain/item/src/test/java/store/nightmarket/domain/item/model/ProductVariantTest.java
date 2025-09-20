package store.nightmarket.domain.item.model;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.exception.QuantityException;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

class ProductVariantTest {

	@Test
	@DisplayName("재고 수량이 구매할 수량보다 많을때 수량이 감소한다.")
	void shouldReduceQuantityWhenProductVariantIsBiggerThanBuyQuantity() {
		// given
		ProductVariant cpuProductVariant = testCPUProductVariant(UUID.randomUUID(), 100);
		Quantity purchaseQuantity = new Quantity(BigDecimal.valueOf(20));

		// when
		cpuProductVariant.purchase(purchaseQuantity);

		// then
		Quantity expectedQuantity = new Quantity(BigDecimal.valueOf(80));

		assertThat(cpuProductVariant.getQuantity()).isEqualTo(expectedQuantity);
	}

	@Test
	@DisplayName("구매 상품의 수량이 충분치 않을때 예외를 던진다.")
	void shouldThrowQuantityExceptionWhenPurchaseProductQuantityIsInsufficient() {
		// given
		ProductVariant cpuProductVariant = testCPUProductVariant(UUID.randomUUID(), 100);
		Quantity purchaseQuantity = new Quantity(BigDecimal.valueOf(120));

		// when
		// then
		assertThatThrownBy(() -> cpuProductVariant.purchase(purchaseQuantity))
			.isInstanceOf(QuantityException.class);
	}

	@Test
	@DisplayName("상품의 아이디가 다를때 True를 반환한다.")
	void shouldReturnTrueWhenProductVariantIdIsDifferent() {
		// given
		ProductVariant cpuProductVariant = testCPUProductVariant(UUID.randomUUID(), 200);
		ProductVariantId otherId = new ProductVariantId(UUID.randomUUID());

		// when
		boolean result = cpuProductVariant.isNotSameAsProduct(otherId);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("상품의 아이디가 같을때 False를 반환한다.")
	void shouldReturnFalseWhenProductVariantIdIsEqual() {
		// given
		UUID sameId = UUID.randomUUID();
		ProductVariant cpuProductVariant = testCPUProductVariant(sameId, 200);
		ProductVariantId otherId = new ProductVariantId(sameId);

		// when
		boolean result = cpuProductVariant.isNotSameAsProduct(otherId);

		// then
		assertThat(result).isFalse();
	}

	private ProductVariant testCPUProductVariant(
		UUID cpuId,
		int cpuQuantity
	) {
		UUID productId = UUID.randomUUID();
		UUID sellerId = UUID.randomUUID();

		return TestItemFactory.createProductVariant(
			cpuId,
			productId,
			sellerId,
			"SKUCode",
			cpuQuantity
		);
	}

}
