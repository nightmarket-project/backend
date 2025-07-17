package store.nightmarket.domain.item.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.domain.item.fixture.TestShoppingBasketFactory;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.service.dto.RestoreItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.RestoreItemDomainServiceDto.Input;
import store.nightmarket.domain.item.valueobject.Quantity;

class RestoreItemDomainServiceDtoTest {

    private RestoreItemDomainServiceDto service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new RestoreItemDomainServiceDto();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("서비스 실행시 구매 취소 상품이 반환하고 재고가 복구된다.")
    void shouldReturnEventWithProductVariantAndIncreaseProductQuantityWhenExecuted() {
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
        Quantity expectedQuantity = new Quantity(BigDecimal.valueOf(220));

        softly.assertThat(event).isNotNull();
        softly.assertThat(event.getProductVariant())
            .isEqualTo(productVariant);
        softly.assertThat(event.getProductVariant().getQuantity())
            .isEqualTo(expectedQuantity);
        softly.assertAll();
    }

    @Test
    @DisplayName("서비스 실행시 상품 아이디가 다를때 예외를 발생한다.")
    void shouldThrowExceptionWhenServiceExecutedAndProductVariantIsDifferent() {
        // given
        UUID productVariantId = UUID.randomUUID();
        UUID otherProductVariantId = UUID.randomUUID();
        ProductVariant productVariant = testProductVariant(
            productVariantId,
            20
        );
        ShoppingBasketProduct shoppingBasketProduct = testShoppingBasketProduct(
            otherProductVariantId,
            2
        );

        Input input = Input.builder()
            .productVariant(productVariant)
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
