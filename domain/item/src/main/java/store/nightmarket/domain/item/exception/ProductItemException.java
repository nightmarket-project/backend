package store.nightmarket.domain.item.exception;

import store.nightmarket.common.exception.DomainException;

public class ProductItemException extends DomainException {

    public ProductItemException() {}

    public ProductItemException(String message) {
        super(message);
    }

}
