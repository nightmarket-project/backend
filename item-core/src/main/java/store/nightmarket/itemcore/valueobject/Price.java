package store.nightmarket.itemcore.valueobject;

import store.nightmarket.itemcore.exception.PriceException;
import store.nightmarket.itemcore.exception.QuantityException;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    private final BigDecimal amount;

    public Price(BigDecimal amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(BigDecimal amount) {
        if(amount == null) {
            throw new PriceException("Amount cannot be null");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PriceException("The price must be greater than zero");
        }
    }

    public Price add(Price other) {
        return new Price(amount.add(other.amount));
    }

    public Price subtract(Price other) {
        return new Price(amount.subtract(other.amount));
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Price other = (Price)obj;
        return Objects.equals(amount, other.amount);
    }
}
