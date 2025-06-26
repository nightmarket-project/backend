package store.nightmarket.domain.item.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.fixture.TestShoppingBasketFactory;
import store.nightmarket.domain.item.fixture.TestInventoryFactory;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemcore.valueobject.Quantity;

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
        String errorMessages = inventory.getErrorMessages(shoppingBasket);

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
        String errorMessages = inventory.getErrorMessages(shoppingBasket);

        // then
        softly.assertThat(errorMessages).isNotEmpty();
        softly.assertThat(errorMessages).isEqualTo(
            """
                Not enough stock: CPU
                Not enough stock: RAM
                """
        );
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
        softly.assertThat(inventory.getInventory().getFirst().getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(9)));
        softly.assertThat(inventory.getInventory().getLast().getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(9)));
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
