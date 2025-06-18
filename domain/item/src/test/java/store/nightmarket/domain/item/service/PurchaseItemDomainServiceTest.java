package store.nightmarket.domain.item.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.fixture.CartFactory;
import store.nightmarket.domain.item.fixture.InventoryFactory;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.model.InventoryProduct;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemcore.valueobject.Quantity;

class PurchaseItemDomainServiceTest {

    private SoftAssertions softly;
    private PurchaseItemDomainService service;
    private UUID cpuId;
    private UUID ramId;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        service = new PurchaseItemDomainService();
        cpuId = UUID.randomUUID();
        ramId = UUID.randomUUID();
    }

    @Test
    @DisplayName("서비스 실행 시 장바구니를 포함한 이벤트가 반환되고, 재고 수량이 차감된다")
    void shouldReturnCartAndDecreaseInventoryQuantitiesWhenExecuted() {
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
        softly.assertThat(event).isNotNull();
        softly.assertThat(event.getCart()).isEqualTo(cart);
        softly.assertThat(inventory.getInventory().getFirst().getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(90)));
        softly.assertThat(inventory.getInventory().getLast().getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(90)));
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
