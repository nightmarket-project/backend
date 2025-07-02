package store.nightmarket.domain.item.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
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
import store.nightmarket.domain.item.service.dto.CancelPurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.CancelPurchaseItemDomainServiceDto.Input;
import store.nightmarket.domain.item.valueobject.Quantity;

class CancelPurchaseItemDomainServiceTest {

    private CancelPurchaseItemDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new CancelPurchaseItemDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("서비스 실행시 제품의 수량이 구매했던 수량만큼 증가한다.")
    void shouldRestoreProductVariantQuantityWhenServiceExecuted() {
        // given
        UUID cpuId = UUID.randomUUID();
        ProductVariant productVariant = testCPUProductVariant(100, cpuId);
        ShoppingBasketProduct shoppingBasketProduct = testShoppingBasketProduct(10, cpuId);

        Input input = Input.builder()
            .productVariant(productVariant)
            .shoppingBasketProduct(shoppingBasketProduct)
            .build();

        // when
        Event event = service.execute(input);

        // then
        Quantity remainingQuantity = new Quantity(BigDecimal.valueOf(110));
        softly.assertThat(event)
            .isNotNull();
        softly.assertThat(event.getProductVariant())
            .isEqualTo(productVariant);
        softly.assertThat(event.getProductVariant().getQuantity())
            .isEqualTo(remainingQuantity);
        softly.assertAll();
    }

    @Test
    @DisplayName("서비스 실행시 상품 아이디가 다를때 예외를 발생한다.")
    void shouldThrowExceptionWhenServiceExecutedAndProductVariantIsDifferent() {
        // given
        UUID cpuId = UUID.randomUUID();
        UUID otherCpuId = UUID.randomUUID();
        ProductVariant productVariant = testCPUProductVariant(100, cpuId);
        ShoppingBasketProduct shoppingBasketProduct = testShoppingBasketProduct(10, otherCpuId);

        Input input = Input.builder()
            .productVariant(productVariant)
            .shoppingBasketProduct(shoppingBasketProduct)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ProductException.class);
    }

    private ProductVariant testCPUProductVariant(
        int cpuQuantity,
        UUID cpuId
    ) {
        UUID productId = UUID.randomUUID();
        UUID sellerId = UUID.randomUUID();
        UUID variantOptionValueId = UUID.randomUUID();
        UUID optionGroupId = UUID.randomUUID();
        UUID optionValueId = UUID.randomUUID();

        return TestItemFactory.createProductVariant(
            cpuId,
            productId,
            sellerId,
            "SKUCode",
            cpuQuantity,
            List.of(
                TestItemFactory.createVariantOptionValue(
                    variantOptionValueId,
                    cpuId,
                    TestItemFactory.createOptionGroup(
                        optionGroupId,
                        productId,
                        "core",
                        1,
                        List.of(
                            TestItemFactory.createOptionValue(
                                optionValueId,
                                optionGroupId,
                                "4코어",
                                10000,
                                1
                            )
                        )
                    ),
                    TestItemFactory.createOptionValue(
                        optionValueId,
                        optionGroupId,
                        "4코어",
                        10000,
                        1
                    )
                )
            )
        );
    }

    private ShoppingBasketProduct testShoppingBasketProduct(
        int cpuQuantity,
        UUID cpuId
    ) {
        return TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            cpuId,
            "CPU",
            cpuQuantity,
            10000
        );
    }

}
