package store.nightmarket.domain.item.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.fixture.TestInventoryFactory;
import store.nightmarket.itemcore.valueobject.Quantity;

class InventoryProductTest {

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("요청 수량보다 많은 수량을 가지고 있을때 Optional empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenQuantityIsSufficient() {
        // given
        InventoryProduct monitor = TestInventoryFactory.createInventoryProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "monitor",
            100,
            LocalDate.now()
        );
        // when
        String quantityErrorMessage = monitor.getQuantityErrorMessage(
                new Quantity(BigDecimal.valueOf(10)))
            .orElse("");
        // then
        assertThat(quantityErrorMessage).isEmpty();
    }

    @Test
    @DisplayName("요청 수량보다 적은 수량을 가지고 있을때 에러 메시지를 반환한다.")
    void shouldReturnErrorMessageWhenQuantityIsInSufficient() {
        // given
        InventoryProduct monitor = TestInventoryFactory.createInventoryProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "monitor",
            10,
            LocalDate.now()
        );
        // when
        String quantityErrorMessage = monitor.getQuantityErrorMessage(
                new Quantity(BigDecimal.valueOf(11)))
            .orElse("");
        // then
        softly.assertThat(quantityErrorMessage).isNotEmpty();
        softly.assertThat(quantityErrorMessage).isEqualTo(
            "Not enough stock: " + monitor.getName().getValue() + "\n");
    }

    @Test
    @DisplayName("상품을 구매하면 재고 수량이 감소한다")
    void shouldDecreaseQuantityWhenProductIsPurchased() {
        // given
        InventoryProduct monitor = TestInventoryFactory.createInventoryProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "monitor",
            100,
            LocalDate.now()
        );
        Quantity buyQuantity = new Quantity(BigDecimal.valueOf(10));

        // when
        monitor.purchase(buyQuantity);

        // then
        assertThat(monitor.getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(90)));
    }

}
