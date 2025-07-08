package store.nightmarket.domain.item.valueobject;

import lombok.Getter;
import store.nightmarket.domain.item.exception.QuantityException;

import java.math.BigDecimal;
import java.util.Objects;

public record Quantity(BigDecimal value) {

    private static final BigDecimal MAX_QUANTITY = BigDecimal.valueOf(Integer.MAX_VALUE);

    public Quantity {
        validate(value);
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

    public boolean isGreaterThanOrEqualTo(Quantity other) {
        return value.compareTo(other.value) >= 0;
    }

    public boolean isLessThan(Quantity other) {
        return value.compareTo(other.value) < 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Quantity other = (Quantity) obj;
        return Objects.equals(value, other.value);
    }

}
