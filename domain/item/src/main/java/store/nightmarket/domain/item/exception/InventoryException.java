package store.nightmarket.domain.item.exception;

import store.nightmarket.common.exception.DomainException;

public class InventoryException extends DomainException {

    public InventoryException() {}

    public InventoryException(String message) {
        super(message);
    }

}
