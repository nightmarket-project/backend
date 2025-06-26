package store.nightmarket.domain.item.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.InventoryException;
import store.nightmarket.domain.item.fixture.TestShoppingBasketFactory;
import store.nightmarket.domain.item.fixture.TestInventoryFactory;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.model.InventoryProduct;
import store.nightmarket.domain.item.service.dto.OrderItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.OrderItemDomainServiceDto.Input;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;

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
        Inventory inventory = TestInventoryFactory.createInventory(
            UUID.randomUUID(),
            UUID.randomUUID()
        );
        InventoryProduct cpuInventoryProduct = TestInventoryFactory.createInventoryProduct(
            UUID.randomUUID(),
            cpuId,
            "CPU",
            cpuQuantity,
            LocalDate.now()
        );
        InventoryProduct ramInventoryProduct = TestInventoryFactory.createInventoryProduct(
            UUID.randomUUID(),
            ramId,
            "RAM",
            ramQuantity,
            LocalDate.now()
        );

        inventory.add(cpuInventoryProduct);
        inventory.add(ramInventoryProduct);

        return inventory;
    }

    private ShoppingBasket testCart(
        int cpuQuantity,
        int ramQuantity
    ) {
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            cpuId,
            "cpu",
            cpuQuantity,
            10000
        );
        ShoppingBaseketProduct ramShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
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
