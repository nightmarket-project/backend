package store.nightmarket.domain.item.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.domain.item.fixture.TestShoppingBasketFactory;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

class InventoryTest {

    private SoftAssertions softly;
    private UUID cpuId;
    private UUID ramId;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        cpuId = UUID.randomUUID();
        ramId = UUID.randomUUID();
    }

    @Test
    @DisplayName("창고 수량이 카트 요청 물품 수량보다 많을때 빈 문자열은 반환한다.")
    void ShouldReturnEmptyMessageWhenQuantityIsSufficient() {
        // given
        Inventory inventory = testInventory(100, 100);
        ShoppingBasket shoppingBasket = testCart(10, 10);

        // when
        String errorMessages = inventory.tryOrdering(shoppingBasket);

        // then
        assertThat(errorMessages).isEmpty();
    }

    @Test
    @DisplayName("창고 수량이 카트 요청 물품 수량보다 적을때 에러 메시지를 반환한다.")
    void shouldReturnErrorMessagesWhenQuantityIsInsufficient() {
        // given
        Inventory inventory = testInventory(100, 100);
        ShoppingBasket shoppingBasket = testCart(110, 110);

        // when
        String errorMessages = inventory.tryOrdering(shoppingBasket);

        // then
        softly.assertThat(errorMessages).isNotEmpty();
        softly.assertThat(errorMessages).isEqualTo(
            "재고 부족함: CartProduct{name=CPU}\n" +
                "재고 부족함: CartProduct{name=RAM}"
        );
        softly.assertAll();
    }

    @Test
    @DisplayName("장바구니에 담긴 상품 수량만큼 재고가 차감된다")
    void shouldDecreaseStockQuantitiesWhenCartIsPurchased() {
        // given
        Inventory inventory = testInventory(10, 10);
        ShoppingBasket shoppingBasket = testCart(1, 1);

        // when
        inventory.purchase(shoppingBasket);

        // then
        softly.assertThat(inventory.getInventory().get(new ProductVariantId(cpuId)).getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(9)));
        softly.assertThat(inventory.getInventory().get(new ProductVariantId(ramId)).getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(9)));
        softly.assertAll();
    }

    private Inventory testInventory(
        int cpuQuantity,
        int ramQuantity
    ) {
        Inventory inventory = TestItemFactory.createInventory(
            UUID.randomUUID(),
            new ArrayList<>()
        );

        ProductVariant cpuProductVariant = TestItemFactory.createProductVariant(
            cpuId,
            UUID.randomUUID(),
            UUID.randomUUID(),
            "SKUCode",
            cpuQuantity,
            List.of(
                TestItemFactory.createVariantOptionValue(
                    UUID.randomUUID(),
                    cpuId,
                    TestItemFactory.createOptionGroup(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "core",
                        1,
                        List.of(
                            TestItemFactory.createOptionValue(
                                UUID.randomUUID(),
                                UUID.randomUUID(),
                                "4코어",
                                10000,
                                1
                            )
                        )
                    ),
                    TestItemFactory.createOptionValue(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "4코어",
                        10000,
                        1
                    )
                )
            )
        );

        ProductVariant ramProductVariant = TestItemFactory.createProductVariant(
            ramId,
            UUID.randomUUID(),
            UUID.randomUUID(),
            "SKUCode",
            cpuQuantity,
            List.of(
                TestItemFactory.createVariantOptionValue(
                    UUID.randomUUID(),
                    ramId,
                    TestItemFactory.createOptionGroup(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "GB",
                        1,
                        List.of(
                            TestItemFactory.createOptionValue(
                                UUID.randomUUID(),
                                UUID.randomUUID(),
                                "4",
                                10000,
                                1
                            )
                        )
                    ),
                    TestItemFactory.createOptionValue(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "4",
                        10000,
                        1
                    )
                )
            )
        );

        inventory.add(cpuProductVariant);
        inventory.add(ramProductVariant);

        return inventory;
    }

    private ShoppingBasket testCart(
        int cpuQuantity,
        int ramQuantity
    ) {
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            cpuId,
            "CPU",
            cpuQuantity,
            10000
        );
        ShoppingBaseketProduct ramShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            ramId,
            "RAM",
            ramQuantity,
            10000
        );

        shoppingBasket.add(cpuShoppingBaseketProduct);
        shoppingBasket.add(ramShoppingBaseketProduct);

        return shoppingBasket;
    }

}
