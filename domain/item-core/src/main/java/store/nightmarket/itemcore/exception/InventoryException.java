package store.nightmarket.itemcore.exception;

import store.nightmarket.common.exception.DomainException;

public class InventoryException extends ItemCoreException {

    public InventoryException() {}

    public InventoryException(String message) {
        super(message);
    }

}
