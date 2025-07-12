package store.nightmarket.domain.item.valueobject;

import java.math.BigDecimal;
import java.util.Objects;
import store.nightmarket.domain.item.exception.PriceException;

public record Price(BigDecimal amount) {

    public Price {
        validate(amount);
    }

    private void validate(BigDecimal amount) {
        if (amount == null) {
            throw new PriceException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PriceException("The price must be greater than zero");
        }
    }

    public Price add(Price other) {
        return new Price(amount.add(other.amount));
    }

    public Price subtract(Price other) {
        return new Price(amount.subtract(other.amount));
    }

    public Price multiplyQuantity(Quantity quantity) {
        return new Price(amount.multiply(quantity.value()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Price other = (Price) obj;
        return Objects.equals(amount, other.amount);
    }

}
