package store.nightmarket.domain.order.valueobject;

public class Quantity {

    private final int value;

    public Quantity(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.value = value;
    }

}
