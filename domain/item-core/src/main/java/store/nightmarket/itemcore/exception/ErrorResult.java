package store.nightmarket.itemcore.exception;

import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;

public record ErrorResult(ItemDetailOptionId optionId, String message) {

    @Override
    public String toString() {
        return "ErrorResult [optionId=" + optionId + ", message=" + message + "]";
    }

}
