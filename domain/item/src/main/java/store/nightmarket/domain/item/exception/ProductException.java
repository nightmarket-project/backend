package store.nightmarket.domain.item.exception;

import store.nightmarket.common.exception.DomainException;

public class ProductException extends DomainException {

    public ProductException() {}

    public ProductException(String message) {
        super(message);
    }

}
