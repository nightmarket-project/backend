package store.nightmarket.itemcore.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.InventoryException;
import store.nightmarket.itemcore.fixture.TestItemFactory;
import store.nightmarket.itemcore.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemcore.model.Inventory;
import store.nightmarket.itemcore.model.ProductVariant;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.service.dto.OrderItemDomainServiceDto.Event;
import store.nightmarket.itemcore.service.dto.OrderItemDomainServiceDto.Input;

class OrderItemDomainServiceTest {

    SoftAssertions softly;
    OrderItemDomainService service;
    private UUID cpuId;
    private UUID ramId;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        service = new OrderItemDomainService();
        cpuId = UUID.randomUUID();
        ramId = UUID.randomUUID();
    }

    @Test
    @DisplayName("카트 요청 수량보다 창고 요청 수량이 많을때 카트를 반환한다.")
    void shouldReturnCartWhenInventoryQuantityIsSufficient() {
        // given
        Inventory inventory = testInventory(100, 100);
        ShoppingBasket shoppingBasket = testCart(10, 10);

        Input input = Input.builder()
            .inventory(inventory)
            .shoppingBasket(shoppingBasket)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event.getShoppingBasket()).isEqualTo(shoppingBasket);
    }

    @Test
    @DisplayName("카트 요청 수량보다 창고 요청 수량이 적을때 InventoryException 을 반환한다.")
    void shouldReturnInventoryExceptionWhenInventoryQuantityIsInsufficient() {
        // given
        Inventory inventory = testInventory(100, 100);
        ShoppingBasket shoppingBasket = testCart(10, 110);

        Input input = Input.builder()
            .inventory(inventory)
            .shoppingBasket(shoppingBasket)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(InventoryException.class);
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
            "cpu",
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
