package store.nightmarket.domain.item.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.InventoryException;
import store.nightmarket.domain.item.fixture.CartFactory;
import store.nightmarket.domain.item.fixture.InventoryFactory;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.model.InventoryProduct;
import store.nightmarket.domain.item.service.dto.OrderItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.OrderItemDomainServiceDto.Input;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;

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
        Cart cart = testCart(10, 10);

        Input input = Input.builder()
            .inventory(inventory)
            .cart(cart)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event.getCart()).isEqualTo(cart);
    }

    @Test
    @DisplayName("카트 요청 수량보다 창고 요청 수량이 적을때 InventoryException 을 반환한다.")
    void shouldReturnInventoryExceptionWhenInventoryQuantityIsInsufficient() {
        // given
        Inventory inventory = testInventory(100, 100);
        Cart cart = testCart(10, 110);

        Input input = Input.builder()
            .inventory(inventory)
            .cart(cart)
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
        Inventory inventory = InventoryFactory.createInventory(
            UUID.randomUUID(),
            UUID.randomUUID()
        );
        InventoryProduct cpuInventoryProduct = InventoryFactory.createInventoryProduct(
            UUID.randomUUID(),
            cpuId,
            "CPU",
            cpuQuantity,
            LocalDate.now()
        );
        InventoryProduct ramInventoryProduct = InventoryFactory.createInventoryProduct(
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

    private Cart testCart(
        int cpuQuantity,
        int ramQuantity
    ) {
        Cart cart = CartFactory.createCart();
        CartProduct cpuCartProduct = CartFactory.createCartProduct(
            cpuId,
            "cpu",
            cpuQuantity,
            10000
        );
        CartProduct ramCartProduct = CartFactory.createCartProduct(
            ramId,
            "RAM",
            ramQuantity,
            10000
        );

        cart.add(cpuCartProduct);
        cart.add(ramCartProduct);

        return cart;
    }

}
