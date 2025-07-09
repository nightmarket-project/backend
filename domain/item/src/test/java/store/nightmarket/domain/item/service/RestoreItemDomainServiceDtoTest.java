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
import store.nightmarket.domain.item.model.ShoppingBasket;
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
    @DisplayName("서비스 실행시 장바구니 상품 구매 취소 리스트가 반환하고 재고가 복구된다.")
    void shouldReturnEventWithProductVariantListAndIncreaseProductQuantityWhenExecuted() {
        // given
        UUID cpuId = UUID.randomUUID();
        UUID ramId = UUID.randomUUID();
        ProductVariant cpuProductVariant = testCPUProductVariant(cpuId, 200);
        ProductVariant ramProductVariant = testRAMProductVariant(ramId, 200);
        List<ProductVariant> cancelProductList = List.of(cpuProductVariant, ramProductVariant);
        ShoppingBasket shoppingBasket = testShoppingBasketProduct(cpuId, 20, ramId, 10);

        Input input = Input.builder()
            .productVariantList(cancelProductList)
            .shoppingBasket(shoppingBasket)
            .build();

        // when
        Event event = service.execute(input);

        // then
        Quantity expectedCpuQuantity = new Quantity(BigDecimal.valueOf(220));
        Quantity expectedRamQuantity = new Quantity(BigDecimal.valueOf(210));

        softly.assertThat(event).isNotNull();
        softly.assertThat(event.getProductVariantList())
            .hasSize(2);
        softly.assertThat(event.getProductVariantList().get(0))
            .isEqualTo(cpuProductVariant);
        softly.assertThat(event.getProductVariantList().get(0).getQuantity())
            .isEqualTo(expectedCpuQuantity);
        softly.assertThat(event.getProductVariantList().get(1))
            .isEqualTo(ramProductVariant);
        softly.assertThat(event.getProductVariantList().get(1).getQuantity())
            .isEqualTo(expectedRamQuantity);
        softly.assertAll();
    }

    @Test
    @DisplayName("서비스 실행시 상품 아이디가 다를때 예외를 발생한다.")
    void shouldThrowExceptionWhenServiceExecutedAndProductVariantIsDifferent() {
        // given
        UUID cpuId = UUID.randomUUID();
        UUID ramId = UUID.randomUUID();
        UUID unavailableProductId = UUID.randomUUID();
        ProductVariant cpuProductVariant = testCPUProductVariant(cpuId, 200);
        ProductVariant ramProductVariant = testRAMProductVariant(ramId, 200);
        List<ProductVariant> cancelProductList = List.of(cpuProductVariant, ramProductVariant);
        ShoppingBasket shoppingBasket = testShoppingBasketProduct(cpuId, 20, unavailableProductId, 10);

        Input input = Input.builder()
            .productVariantList(cancelProductList)
            .shoppingBasket(shoppingBasket)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ProductException.class);
    }

    @Test
    @DisplayName("복구 가능한 제품 목록이 비어있을때 예외를 던진다.")
    void shouldThrowExceptionWhenCancelProductListIsEmpty() {
        // given
        UUID cpuId = UUID.randomUUID();
        UUID ramId = UUID.randomUUID();
        List<ProductVariant> cancelProductList = List.of();
        ShoppingBasket shoppingBasket = testShoppingBasketProduct(cpuId, 20, ramId, 10);

        Input input = Input.builder()
            .productVariantList(cancelProductList)
            .shoppingBasket(shoppingBasket)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ProductException.class);
    }

    private ProductVariant testCPUProductVariant(
        UUID cpuId,
        int cpuQuantity
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

    private ProductVariant testRAMProductVariant(
        UUID ramId,
        int ramQuantity
    ) {
        UUID productId = UUID.randomUUID();
        UUID sellerId = UUID.randomUUID();
        UUID variantOptionValueId = UUID.randomUUID();
        UUID optionGroupId = UUID.randomUUID();
        UUID optionValueId = UUID.randomUUID();

        return TestItemFactory.createProductVariant(
            ramId,
            productId,
            sellerId,
            "SKUCode",
            ramQuantity,
            List.of(
                TestItemFactory.createVariantOptionValue(
                    variantOptionValueId,
                    ramId,
                    TestItemFactory.createOptionGroup(
                        optionGroupId,
                        productId,
                        "GB",
                        1,
                        List.of(
                            TestItemFactory.createOptionValue(
                                optionValueId,
                                optionGroupId,
                                "8",
                                20000,
                                1
                            )
                        )
                    ),
                    TestItemFactory.createOptionValue(
                        optionValueId,
                        optionGroupId,
                        "8",
                        20000,
                        1
                    )
                )
            )
        );
    }

    private ShoppingBasket testShoppingBasketProduct(
        UUID cpuId,
        int cpuQuantity,
        UUID ramId,
        int ramQuantity
    ) {
        ShoppingBasket cart = TestShoppingBasketFactory.createCart();
        ShoppingBasketProduct cpu = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            cpuId,
            "CPU",
            cpuQuantity,
            10000
        );
        ShoppingBasketProduct ram = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            ramId,
            "CPU",
            ramQuantity,
            10000
        );

        cart.add(cpu);
        cart.add(ram);

        return cart;
    }

}
