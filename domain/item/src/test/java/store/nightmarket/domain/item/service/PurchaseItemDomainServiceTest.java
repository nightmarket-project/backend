package store.nightmarket.domain.item.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.exception.QuantityException;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.domain.item.fixture.TestShoppingBasketFactory;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;
import store.nightmarket.domain.item.valueobject.Quantity;

class PurchaseItemDomainServiceTest {

    private PurchaseItemDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new PurchaseItemDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("서비스 실행 시 장바구니 상품를 포함한 이벤트가 반환되고, 재고 수량이 차감된다")
    void shouldReturnEventWithBasketProductAndDecreaseProductQuantityWhenServiceExecuted() {
        // given
        UUID productVariantId = UUID.randomUUID();
        ProductVariant productVariant = testProductVariant(
            productVariantId,
            200
        );
        ShoppingBasketProduct shoppingBasketProduct = testShoppingBasketProduct(
            productVariantId,
            20
        );

        Input input = Input.builder()
            .productVariant(productVariant)
            .shoppingBasketProduct(shoppingBasketProduct)
            .build();

        // when
        Event event = service.execute(input);

        // then
        Quantity expectedCpuQuantity = new Quantity(BigDecimal.valueOf(180));

        softly.assertThat(event)
            .isNotNull();
        softly.assertThat(productVariant.getQuantity())
            .isEqualTo(expectedCpuQuantity);
        softly.assertThat(event.getProductVariant())
            .isEqualTo(productVariant);
        softly.assertAll();
    }

    @Test
    @DisplayName("구매 상품 수량이 충분치 않을때 예외를 던진다.")
    void shouldThrowQuantityExceptionWhenPurchaseProductQuantityIsInsufficient() {
        // given
        UUID productVariantId = UUID.randomUUID();
        ProductVariant productVariant = testProductVariant(
            productVariantId,
            100
        );
        ShoppingBasketProduct shoppingBasketProduct = testShoppingBasketProduct(
            productVariantId,
            220
        );

        Input input = Input.builder()
            .productVariant(productVariant)
            .shoppingBasketProduct(shoppingBasketProduct)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("상품 아이디가 다를때 예외를 발생한다.")
    void shouldThrowProductExceptionWhenProductVariantIdIsDifferent() {
        // given
        UUID productVariantId = UUID.randomUUID();
        UUID otherProductVariantId = UUID.randomUUID();
        ProductVariant cpuProductVariant = testProductVariant(
            productVariantId,
            100
        );
        ShoppingBasketProduct shoppingBasketProduct = testShoppingBasketProduct(
            otherProductVariantId,
            20
        );

        Input input = Input.builder()
            .productVariant(cpuProductVariant)
            .shoppingBasketProduct(shoppingBasketProduct)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ProductException.class);
    }

    private ProductVariant testProductVariant(
        UUID productVariantId,
        int productQuantity
    ) {
        UUID productId = UUID.randomUUID();
        UUID sellerId = UUID.randomUUID();

        return TestItemFactory.createProductVariant(
            productVariantId,
            productId,
            sellerId,
            "SKUCode",
            productQuantity
        );
    }

    private ShoppingBasketProduct testShoppingBasketProduct(
        UUID productVariantId,
        int productQuantity
    ) {
        return TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            productVariantId,
            UUID.randomUUID(),
            "상품이름",
            10000,
            productQuantity
        );
    }

}
