package store.nightmarket.itemcore.exception;

import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;

public class ItemDetailOptionException extends ItemCoreException {

    public ItemDetailOptionException(ItemDetailOptionId id) {}

    public ItemDetailOptionException(String message, ItemDetailOptionId id) {
        super(message);
    }

}
