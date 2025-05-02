package store.nightmarket.itemcore.valueobject;

import store.nightmarket.itemcore.exception.QuantityException;

import java.math.BigDecimal;
import java.util.Objects;

public class Quantity {

    private static final BigDecimal MAX_QUANTITY = BigDecimal.valueOf(Integer.MAX_VALUE);
    private final BigDecimal value;

    public Quantity(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (value == null) {
            throw new QuantityException("Quantity cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new QuantityException("Quantity must be positive, Zero");
        }
        if (value.compareTo(MAX_QUANTITY) >= 0) {
            throw new QuantityException("Quantity must be less than to " + MAX_QUANTITY);
        }

    }

    public Quantity add(Quantity other) {
        return new Quantity(value.add(other.value));
    }

    public Quantity subtract(Quantity other) {
        return new Quantity(value.subtract(other.value));
    }

    public boolean isZero() {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Quantity other = (Quantity) obj;
        return Objects.equals(value, other.value);
    }
}
